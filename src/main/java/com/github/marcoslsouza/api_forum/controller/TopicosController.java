package com.github.marcoslsouza.api_forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.api_forum.modelo.Curso;
import com.github.marcoslsouza.api_forum.modelo.Topico;

@RestController
public class TopicosController {

	@RequestMapping("/topicos")
	public List<Topico> lista() {
		Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Spring", "Programação"));
		return Arrays.asList(topico, topico, topico);
	}
}
