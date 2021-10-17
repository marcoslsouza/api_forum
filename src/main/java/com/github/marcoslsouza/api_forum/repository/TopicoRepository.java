package com.github.marcoslsouza.api_forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marcoslsouza.api_forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);

	//List<Topico> findByTitulo();


}
