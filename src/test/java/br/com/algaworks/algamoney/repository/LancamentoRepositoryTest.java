package br.com.algaworks.algamoney.repository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LancamentoRepositoryTest {

	@Autowired
	private LancamentoRepository lancamentoRepository; 
	
	@Test(timeout=3000)
	public void listaCategorias(){
		lancamentoRepository.findAll();
	}
	
	@After
	public void imprimeQuantidade(){
		System.out.println(lancamentoRepository.findAll().size());
	}
	
}
