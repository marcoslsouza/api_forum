package com.github.marcoslsouza.api_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marcoslsouza.api_forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

}
