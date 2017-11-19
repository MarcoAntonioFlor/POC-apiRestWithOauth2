package br.com.algaworks.algamoney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.algaworks.algamoney.dto.LancamentoFilter;
import br.com.algaworks.algamoney.model.Lancamento;
import br.com.algaworks.algamoney.model.Pessoa;
import br.com.algaworks.algamoney.repository.LancamentoRepository;
import br.com.algaworks.algamoney.repository.PessoaRepository;
import br.com.algaworks.algamoney.service.exception.PessoaInexistenteException;
import br.com.algaworks.algamoney.service.facade.LancamentoServiceFacade;

@Service
public class LancamentoService implements LancamentoServiceFacade{
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}

	@Override
	public Optional<Lancamento> salvar(Lancamento lancamento) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findByCodigo(lancamento.getPessoa().getCodigo());
		
		if(pessoa.isPresent())
			if(pessoa.get().isInativo())
				return Optional.ofNullable(lancamentoRepository.save(lancamento));
		
		throw new PessoaInexistenteException();
		
	}

	@Override
	public void remover(Lancamento valor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<? extends Object> atualizar(Long identificador, Lancamento valor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Lancamento> buscarLancamento(Long codigo) {
		Optional<Lancamento> lancamento = lancamentoRepository.findByCodigo(codigo);
		lancamento.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return lancamento;
	}
	
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable){
		return lancamentoRepository.filtrar(filter, pageable);
	}

	@Override
	public void removerLancamentoPorIdentificador(Long codigo) {
		lancamentoRepository.delete(buscarLancamento(codigo).get());
	}
	
	
}
