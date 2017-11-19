package br.com.algaworks.algamoney.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent{

	private static final long serialVersionUID = 8303843782202450681L;

	HttpServletResponse response;
	Long codigo;
	
	public ResourceCreatedEvent(Object source, HttpServletResponse response,
			Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}
	
	public Long getCodigo(){
		return this.codigo;
	}
}
