package br.com.algaworks.algamoney.token;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Incluir o refreh_token na requisição
 * @author marco
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		if(req.getRequestURI().equalsIgnoreCase("/oauth/token")
				&& req.getParameter("grant_type").equalsIgnoreCase("refresh_token")
				&& req.getCookies() != null){
			
			List<Cookie> cookies = Arrays.asList(req.getCookies());
			String refreshToken = cookies
				.stream()
				.filter(cookie -> cookie.getValue().equalsIgnoreCase("refreshToken"))
				.findFirst()
				.get()
				.getValue();
			
			req =  new ServletRequestWrapperRefreshToken(req, refreshToken);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
