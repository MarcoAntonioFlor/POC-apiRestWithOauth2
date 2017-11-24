package br.com.algaworks.algamoney;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.algaworks.algamoney.model.Categoria;
import br.com.algaworks.algamoney.resources.CategoriaResource;
import br.com.algaworks.algamoney.service.CategoriaService;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaResourceTest extends TestCase{
	
	@InjectMocks
	private CategoriaResource categoriaResource;
	
	@Mock
	private CategoriaService categoriaService;
	
	/**
	 * Neste método devem ser colocados códigos que precisam ser executados antes da criação de um objeto da classe de teste, ou seja, 
	 * um código do qual todos os métodos de teste podem tirar algum proveito. Pode ser a criação de uma conexão com o banco de dados, 
	 * por exemplo, ou a leitura de um arquivo no sistema de arquivos.
	 * A anotação que acompanha o método (@BeforeClass) pode ser adicionada a qualquer método, 
	 * e nesse caso, todos os métodos que tiverem essa anotação serão executados na ordem em que aparecem declarados, 
	 * e antes de qualquer caso de teste específico.
	 */
	@BeforeClass
	public static void beforeClassTest(){
		System.out.println("before class ...");
	}
	
	@Before
	public void beforeTezt(){
		MockitoAnnotations.initMocks(this);
		System.out.println("before ... ");
	}
	
	@Test
	public void listarCategoriasEmpty(){
		Mockito.when(categoriaService.listar()).thenReturn(new ArrayList<>());
		assertThat(categoriaResource.listarCategorias(), allOf(
				notNullValue(),
				hasProperty("statusCode", equalTo(HttpStatus.NOT_FOUND)),
				hasProperty("body", equalTo(null))));
	}
	
	@Test
 	//(timeout=500) teste de perfomance
	public void listarCategorias(){
		
		Categoria categoria = new Categoria();
		categoria.setCodigo(1L);
		categoria.setNome("Cartão de crédito");
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(categoria);
		Mockito.when(categoriaService.listar()).thenReturn(categorias);
		assertThat(categoriaResource.listarCategorias(), allOf(
				notNullValue(),
				hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", equalTo(categorias))));
	}
	
	@After
	public void after(){
		System.out.println("after ...");
	}
	
	/**
	 * O método tearDown() é utilizado para a liberação de recursos ao final de cada método de teste. 
	 * Estes recursos geralmente são os que foram obtidos no método setUp().
	 * A anotação @After pode, assim como as demais, ser utilizada com outros métodos.
	 */
	@AfterClass
	public static void afterClassTest(){
		System.out.println("after class...");
	}
}
