package br.com.algaworks.algamoney.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable{

	private static final long serialVersionUID = -7558084639954342359L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo; 
	
	@NotBlank
	private String nome;
	
	
}
