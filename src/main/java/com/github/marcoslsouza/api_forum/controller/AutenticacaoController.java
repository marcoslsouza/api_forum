package com.github.marcoslsouza.api_forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

import com.github.marcoslsouza.api_forum.config.security.TokenService;
import com.github.marcoslsouza.api_forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	// Nao pode ser injetado por padrao, entao implementamos o metodo authenticationManager na classe 
	// SecurityConfiguration
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {
		
		// Criando um objeto UsernamePasswordAuthenticationToken para receber os dados do form
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			
			// authenticate chama a classe AutenticacaoService
			Authentication authentication = this.authManager.authenticate(dadosLogin);
			
			// Criar o token
			// Para extrair o usuario logado e criar o token
			String token = this.tokenService.gerarToken(authentication);
			
			// Teste para ver se está gerando o token
			System.out.println(token);
			
			return ResponseEntity.ok().build();
		} catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
