package com.github.marcoslsouza.api_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marcoslsouza.api_forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso);

}
