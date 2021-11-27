package com.github.marcoslsouza.api_forum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 2871172927784538925L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	// Atributo com o nome do perfil
	@Override
	public String getAuthority() {
		return nome;
	}
}
