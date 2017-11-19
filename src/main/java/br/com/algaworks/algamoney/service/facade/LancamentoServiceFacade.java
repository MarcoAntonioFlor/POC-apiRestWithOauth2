package br.com.algaworks.algamoney.service.facade;

import java.util.Optional;

import br.com.algaworks.algamoney.model.Lancamento;

public interface LancamentoServiceFacade extends CrudBasicService<Lancamento>{
	
	public Optional<Lancamento> buscarLancamento(Long codigo);
	
	public void removerLancamentoPorIdentificador(Long codigo);
	
}
