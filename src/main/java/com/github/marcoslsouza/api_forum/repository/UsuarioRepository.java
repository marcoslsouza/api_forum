package com.github.marcoslsouza.api_forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marcoslsouza.api_forum.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Pesquisa o usuario pelo campo email
	Optional<Usuario> findByEmail(String email);
}
