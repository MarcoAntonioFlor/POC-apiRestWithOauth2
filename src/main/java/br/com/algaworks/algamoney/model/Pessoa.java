package br.com.algaworks.algamoney.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "pessoa")
@Data
@DynamicUpdate
public class Pessoa implements Serializable{
	
	private static final long serialVersionUID = -3994298556090210807L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	private String nome;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="endereco", referencedColumnName="codigo", insertable=true)
	private Endereco endereco;
	
	@Getter
	private Boolean ativo;
	
	@JsonIgnore
	@Transient
	public boolean isInativo(){
		return !this.ativo;
	}
}
