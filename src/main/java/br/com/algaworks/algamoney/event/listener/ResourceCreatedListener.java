package br.com.algaworks.algamoney.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.algaworks.algamoney.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent>{
	
	private static String LOCATION = "location";
	
	@Override
	public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
		HttpServletResponse response = resourceCreatedEvent.getResponse();
		Long codigo = resourceCreatedEvent.getCodigo();
		
		createHeaderLocationPostCreated(codigo, response);
		
	}

	private void createHeaderLocationPostCreated(Long codigo, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/{codigo}")
				.buildAndExpand(codigo)
				.toUri();
		response.setHeader(LOCATION, uri.toASCIIString());
	}

}
