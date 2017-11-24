package br.com.algaworks.algamoney.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaRepositoryTest {
	
	@Autowired
	private CategoriasRepository categoriaRepository; 
	
	@Test(timeout=3000)
	public void listaCategorias(){
		categoriaRepository.findAll();
	}
	
}
