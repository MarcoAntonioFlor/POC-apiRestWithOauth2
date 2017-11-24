package br.com.algaworks.algamoney.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.apache.catalina.util.ParameterMap;

public class ServletRequestWrapperRefreshToken extends HttpServletRequestWrapper{
	
	private String refreshToken; 
	
	public ServletRequestWrapperRefreshToken(HttpServletRequest request, String refreshToken) {
		super(request);
		this.refreshToken = refreshToken;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		ParameterMap<String, String[]> mapper = new ParameterMap<>(getRequest().getParameterMap());
		mapper.put("refresh_token", new String[]{refreshToken});
		mapper.setLocked(true);
		return mapper;
	}
}
