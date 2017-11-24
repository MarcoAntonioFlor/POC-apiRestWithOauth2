package br.com.algaworks.algamoney.config;

import static br.com.algaworks.algamoney.enumerator.ScopesOauth.READ;
import static br.com.algaworks.algamoney.enumerator.ScopesOauth.WRITE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	public static String GRANT_TYPE_PASSWORD = "password";
	public static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("@ngular0")
			.scopes(READ.getScope(), WRITE.getScope())
			.authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_REFRESH_TOKEN)
			.accessTokenValiditySeconds(20)
			.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter(){
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//Assinatura do token
		jwtAccessTokenConverter.setSigningKey("algaworks");
		return jwtAccessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(accessTokenConverter());
	}
}
