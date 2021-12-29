package com.github.marcoslsouza.api_forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

import com.github.marcoslsouza.api_forum.config.security.TokenService;
import com.github.marcoslsouza.api_forum.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {
		//System.out.println("Email: " + form.getEmail());
		//System.out.println("Senha: " + form.getSenha());
		
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
		
			Authentication authentication = this.authManager.authenticate(dadosLogin);
		
			String token = tokenService.gerarToken(authentication);
			
			System.out.println("Token: " + token);
			
			return ResponseEntity.ok().build();
		} catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
