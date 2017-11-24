package br.com.algaworks.algamoney.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Classe de configuração para o CORS ( Cross-origin resource sharing )
 * Esta configuração permiti o uso de CORS globalmente com o auth2 sem necessidade de incluir um filter
 * @author marco
 *
 */
@Configuration
public class CorsConfig {

	private String originPermitida = "http://localhost:8081"; // TODO: Configurar para diferentes ambientes
	
	@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        CorsConfiguration configAutenticacao = new CorsConfiguration();
        configAutenticacao.setAllowCredentials(true);
        configAutenticacao.addAllowedOrigin(originPermitida);
        configAutenticacao.addAllowedHeader("Authorization");
        configAutenticacao.addAllowedHeader("Content-Type");
        configAutenticacao.addAllowedHeader("Accept");
        configAutenticacao.addAllowedMethod("POST");
        configAutenticacao.addAllowedMethod("GET");
        configAutenticacao.addAllowedMethod("DELETE");
        configAutenticacao.addAllowedMethod("PUT");
        configAutenticacao.addAllowedMethod("OPTIONS");
        configAutenticacao.setMaxAge(3600L);
        source.registerCorsConfiguration("/oauth/token", configAutenticacao);
        
        //source.registerCorsConfiguration("/**", configAutenticacao); // Global para todas as URLs/paths da aplicação
        
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
	
	
}
	