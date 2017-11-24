package br.com.algaworks.algamoney.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author marco
 * 
 * Classe responsavel por interceptar o envio do token para retirar o refresh
 * token do body
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{

	public static final String POST_ACCESS_TOKEN = "postAccessToken";
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		//Filtro adicional para limitar a chamada do beforeBodyWrite()
		return returnType.getMethod().getName().equals(POST_ACCESS_TOKEN);
	}

	/**
	 * Remove token do body da requisição e adiciona a um cookie seguro
	 */
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		
		adicionaRefrehTokenNoCookie(refreshToken, req, resp);
		removeRefreshTokenDoBody(token);
		return token;
	}

	/**
	 * Remove token do body
	 * @param token
	 */
	private void removeRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	/**
	 * Adiciona refresh token a um cookie seguro
	 * @param refreshToken
	 * @param req
	 * @param resp
	 */
	private void adicionaRefrehTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); //TODO Mudar para true em produção ( https )
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000);
		
		resp.addCookie(refreshTokenCookie);
		
	}

}
