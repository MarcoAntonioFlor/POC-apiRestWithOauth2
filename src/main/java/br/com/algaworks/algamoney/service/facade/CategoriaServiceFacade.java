package br.com.algaworks.algamoney.service.facade;

import java.util.Optional;

import br.com.algaworks.algamoney.model.Categoria;

public interface CategoriaServiceFacade extends CrudBasicService<Categoria>{
	
	/**
	 * Busca objeto por indentificador
	 * @param id
	 * @return
	 */
	Optional<Categoria> buscaPorIdentificador(final Long identificador);
	
	
	void remover(Long identificador);
}

