package br.com.algaworks.algamoney.repository.query.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.com.algaworks.algamoney.dto.LancamentoFilter;
import br.com.algaworks.algamoney.model.Lancamento;
import br.com.algaworks.algamoney.model.Lancamento_;
import br.com.algaworks.algamoney.repository.query.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager entity;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = entity.getCriteriaBuilder();
		CriteriaQuery<Lancamento> createQuery = builder.createQuery(Lancamento.class);
		Root<Lancamento> rootLancamento = createQuery.from(Lancamento.class);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, rootLancamento);
		createQuery.where(predicates);
		TypedQuery<Lancamento> query = entity.createQuery(createQuery);
		paginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private Long  total(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder builder = entity.getCriteriaBuilder();
		CriteriaQuery<Long> createQuery = builder.createQuery(Long.class);
		Root<Lancamento> rootLancamento = createQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, rootLancamento);
		createQuery.where(predicates);
		createQuery.select(builder.count(rootLancamento));
		
		return entity.createQuery(createQuery).getSingleResult();
	}

	private void paginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder cb,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>(); 
		
		if(lancamentoFilter.getDataVencimentoDe() != null 
				&& lancamentoFilter.getDataVencimentoAte() != null){
			Path<LocalDate> dataVencimentoDe = root.get(Lancamento_.dataVencimento);
			Path<LocalDate> dataVencimentoAte = root.get(Lancamento_.dataVencimento);
			predicates.add(cb.and(cb.lessThanOrEqualTo(dataVencimentoDe, lancamentoFilter.getDataVencimentoDe()), 
					cb.greaterThanOrEqualTo(dataVencimentoAte, dataVencimentoAte)));
		}
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())){
			predicates.add(cb.like(cb.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public Specification<Lancamento> filtrarComSpecification(LancamentoFilter lancamentoFilter){
		
		return new Specification<Lancamento>() {
			@Override
			public Predicate toPredicate(Root<Lancamento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(criarRestricoes(lancamentoFilter, cb, root));
			}
		};
	}

}
