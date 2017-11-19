package br.com.algaworks.algamoney.exceptionHandler;

import lombok.Data;

@Data
public class MensagemErro {
	
	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	
	public MensagemErro(String mensagemUsuario, String mensagemDesenvolvedor){
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
}
