package br.com.algaworks.algamoney.service.facade;

import java.util.List;
import java.util.Optional;

public abstract interface CrudBasicService <T extends Object> {
	
	/**
	 * Listar todos os objetos 
	 * @return
	 */
	List<? extends Object> listar();
	
	/**
	 * Altera T
	 * @param valor
	 * @return
	 */
	Optional<? extends Object> salvar(final T valor);

	/**
	 * Remover pelo identificador
	 * @param valor
	 */
	void remover(final T valor);
	
	/**
	 * Atualiza dados 
	 * @param valor
	 * @return
	 */
	Optional<? extends Object> atualizar(final Long identificador, final T valor);

}
