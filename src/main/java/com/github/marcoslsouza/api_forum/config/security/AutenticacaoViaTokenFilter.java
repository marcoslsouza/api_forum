package com.github.marcoslsouza.api_forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.marcoslsouza.api_forum.modelo.Usuario;
import com.github.marcoslsouza.api_forum.repository.UsuarioRepository;

// Obs: Esta classe precisa ser registrada na classe SecurityConfiguration

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	// Nao podemos fazer a injecao de dependencias no filtro.
	// Entao fazemos na classe SecurityConfiguration
	private TokenService tokenService;
	private UsuarioRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
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
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		
		// Recupera o usuario
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		
		// null na senha, pois ja esta autenticado
		UsernamePasswordAuthenticationToken autentication = 
				new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(autentication);
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
