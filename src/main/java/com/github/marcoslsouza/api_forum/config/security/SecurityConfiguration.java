package com.github.marcoslsouza.api_forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.marcoslsouza.api_forum.repository.UsuarioRepository;

// Ativa o Spring Security
@EnableWebSecurity

// E uma classe de configuracao e com beans e etc.
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Classe com logica de autenticacao "autenticacaoService"
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated() // Qualquer outra requisicao devera autenticar
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//.and().formLogin(); // Gerar um formulario de autenticacao.
			// addFilterBefore indica que AutenticacaoViaTokenFilter() vai ser executado antes de UsernamePasswordAuthenticationFilter
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		
		// Comentando: .and().formLogin();
		// E adicionando:
		// .and().csrf().disable()
		// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Indica que nao e para criar sessao
	}
	
	// Confikguracoes de recursos estaticos(js, css, imagens e etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		
	}
	
	/* public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}*/
}
