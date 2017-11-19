package br.com.algaworks.algamoney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algaworks.algamoney.enumerator.Estado;
import br.com.algaworks.algamoney.model.Pessoa;
import br.com.algaworks.algamoney.repository.PessoaRepository;
import br.com.algaworks.algamoney.service.facade.PessoaServiceFacade;

@Service
public class PessoaService implements PessoaServiceFacade{
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Optional<Pessoa> buscaPorIdentificador(Long identificador) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCodigo(identificador);
		pessoa.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoa; 
	}

	@Override
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@Override
	public Optional<Pessoa> salvar(Pessoa pessoa) {
		return Optional.of(pessoaRepository.save(pessoa));
	}

	@Override
	public void remover(Pessoa pessoa) {
		pessoaRepository.delete(pessoa);
	}

	@Override
	public void removerPessoaPorId(Long identificador) throws EmptyResultDataAccessException{
		pessoaRepository.delete(identificador);
	}

	@Override
	public Optional<Pessoa> atualizar(Long identificador, Pessoa pessoa) throws EmptyResultDataAccessException{

		Optional<Pessoa> pessoaSalva = buscaPorIdentificador(identificador);
		pessoaSalva.orElseThrow(() -> new EmptyResultDataAccessException(1));
		Estado estado = Estado.fromNome(pessoa.getEndereco().getEstado().getNome());
		pessoaSalva.get().getEndereco().setEstado(estado);
		BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "codigo");
		return Optional.of(pessoaRepository.save(pessoaSalva.get()));
	}
	
	public void atualizarPropriedadeAtiva(Long identificador, Boolean ativo){
		Optional<Pessoa> pessoa = buscaPorIdentificador(identificador);
		pessoa.orElseThrow(() -> new EmptyResultDataAccessException(1));
		pessoa.get().setAtivo(ativo);
		pessoaRepository.save(pessoa.get());
	}
}
