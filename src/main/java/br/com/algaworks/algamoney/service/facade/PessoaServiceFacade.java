package br.com.algaworks.algamoney.service.facade;

import java.util.Optional;

import br.com.algaworks.algamoney.model.Pessoa;

public interface PessoaServiceFacade extends CrudBasicService<Pessoa>{
	
	/**
	 * Busca objeto por indentificador
	 * @param id
	 * @return
	 */
	Optional<Pessoa> buscaPorIdentificador(final Long identificador);
	
	/**
	 * Remove pessoa por id
	 * @param id
	 */
	void removerPessoaPorId(Long identificador);
}
