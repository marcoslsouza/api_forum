package com.github.marcoslsouza.api_forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Ativa o Spring Security
@EnableWebSecurity

// E uma classe de configuracao e com beans e etc.
@Configuration
public class SecurityCondiguration extends WebSecurityConfigurerAdapter {

	// Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	// Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.anyRequest().authenticated() // Qualquer outra requisicao devera autenticar
			.and().formLogin(); // Gerar um formulario de autenticacao
	}
	
	// Confikguracoes de recursos estaticos(js, css, imagens e etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		
	}
}
