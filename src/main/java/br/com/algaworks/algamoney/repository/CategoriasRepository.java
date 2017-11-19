package br.com.algaworks.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algaworks.algamoney.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Long>{

}
