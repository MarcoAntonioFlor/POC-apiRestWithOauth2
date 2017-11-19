package br.com.algaworks.algamoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algaworks.algamoney.model.Lancamento;
import br.com.algaworks.algamoney.repository.query.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
	Optional<Lancamento> findByCodigo(Long codigo);
}
