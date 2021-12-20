package com.github.marcoslsouza.api_forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

// Obs: Esta classe precisa ser registrada na classe SecurityConfiguration

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	// Nao podemos fazer a injecao de dependencias no filtro.
	// Entao fazemos na classe SecurityConfiguration
	private TokenService tokenService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Recupera o token
		String token = recuperarToken(request);
		
		System.out.println("Token recuperado: " + token);
		// Validar token
		boolean valido = this.tokenService.isTokenValido(token);
		System.out.println("Token valido: " + valido);
		
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		// Verifica se o token esta correto
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
