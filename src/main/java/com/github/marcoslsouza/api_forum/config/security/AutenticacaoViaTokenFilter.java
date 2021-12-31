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

// Precisa configurar essa classe em SecurityConfigurations
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		// Validar o token
		//System.out.println("token: " + token);
		boolean valido = tokenService.isTokenValido(token);
		// System.out.println("Token valido: " + valido);
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		// Ao criar o usuario no TokenService foi passado o id do usuario (.setSubject(logado.getId().toString()))
		Long idUsuario = this.tokenService.getIdUsuario(token);
		Usuario usuario = this.usuarioRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword(), usuario.getAuthorities());
		// Considera que esta autenticado
		SecurityContextHolder.getContext().setAuthentication(autentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		// Pegar apartir do espaco Bearer <a partir daqui>"
		return token.substring(7, token.length());
	}

}
