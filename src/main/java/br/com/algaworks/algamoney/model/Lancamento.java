package br.com.algaworks.algamoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.algaworks.algamoney.enumerator.TipoLancamento;
import lombok.Data;

@Data
@Entity
@Table(name="lancamento")
public class Lancamento implements Serializable{
	
	private static final long serialVersionUID = -4009052049104281103L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	private String descricao;
	
	@Column(name="data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	@Column(name="data_vencimento")
	private LocalDate dataVencimento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="codigo_categoria")
	private Categoria categoria;

	@NotNull
	@ManyToOne
	@JoinColumn(name="codigo_pessoa")
	private Pessoa pessoa;
	
	
	
}
