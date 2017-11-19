package br.com.algaworks.algamoney.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.algaworks.algamoney.dto.LancamentoFilter;
import br.com.algaworks.algamoney.model.Lancamento;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
