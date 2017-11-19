package br.com.algaworks.algamoney.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.algaworks.algamoney.enumerator.Estado;
import lombok.Data;

@Entity
@Table(name="endereco")
@Data
public class Endereco implements Serializable{
	
	private static final long serialVersionUID = 5778006729913414762L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	
	@Enumerated
	private Estado estado;
	
}
