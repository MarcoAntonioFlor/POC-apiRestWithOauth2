package br.com.algaworks.algamoney.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	private List<String> listOrigin = new ArrayList<>(); //TODO configurar para verios ambientes
	
	private String clientAngular = "http://localhost:8000";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest  request = (HttpServletRequest) req;
		HttpServletResponse  response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		listOrigin.add(clientAngular);
		adicionaOrigensPermitidas(response, listOrigin);
		
		if(request.getMethod().equals("OPTIONS") && listOrigin.contains(request.getHeader(clientAngular))){
			adicionaHeaders(response);
		}else{
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	private void adicionaOrigensPermitidas(HttpServletResponse response, List<String> listOrigins){
		listOrigin.forEach(o -> response.setHeader("Access-Control-Allow-Origin", o));
	}
	
	
	private void adicionaHeaders(HttpServletResponse response){
		
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void destroy() {
	}
}
