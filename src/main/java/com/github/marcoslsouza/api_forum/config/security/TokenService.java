package com.github.marcoslsouza.api_forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.marcoslsouza.api_forum.modelo.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	// api_forum.jwt.expiration propriedade do application.properties
	@Value("${api_forum.jwt.expiration}")
	private String expiration;
	
	@Value("${api_forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		
		// setIssuer quem disponibilizou o token
		// setSubject usuario logado
		// setIssuedAt data que foi gerado o token. Trabalha ainda com Date
		// setExpiration tempo de expiracao
		// signWith criptografia do token
		// compact compactar e gerar a string
		
		// Pegar o usuario logado. Devolve um Object por isso precisa fazer um cast (Usuario)
		Usuario logado = (Usuario) authentication.getPrincipal();
		
		Date hoje = new Date();
		
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do forum")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValido(String token) {
		try {
			// parser vai descripotografar e verificar se e valido.
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		// Retorna o id do usuario
		return Long.parseLong(claims.getSubject());
	}
}
