package br.com.algaworks.algamoney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.algaworks.algamoney.model.Categoria;
import br.com.algaworks.algamoney.repository.CategoriasRepository;
import br.com.algaworks.algamoney.service.facade.CategoriaServiceFacade;

@Service
public class CategoriaService implements CategoriaServiceFacade{

	@Autowired
	private CategoriasRepository categoriasRepository;
	
	@Override
	public List<Categoria> listar() {
		return categoriasRepository.findAll();
	}

	@Override
	public Optional<Categoria> salvar(final Categoria categoria) {
		return Optional.ofNullable(categoriasRepository.save(categoria));
	}

	@Override
	public Optional<Categoria> buscaPorIdentificador(final Long id){
		return Optional.ofNullable(categoriasRepository.findOne(id));
	}

	@Override
	public void remover(final Categoria categoria) {
		categoriasRepository.delete(categoria);
	}

	@Override
	public void remover(Long identificador) {
		categoriasRepository.delete(identificador);
	}

	@Override
	public Optional<? extends Object> atualizar(Long identificador, Categoria valor) {
		// TODO Auto-generated method stub
		return null;
	}
}
