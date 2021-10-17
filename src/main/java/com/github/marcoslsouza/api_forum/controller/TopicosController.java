package com.github.marcoslsouza.api_forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.api_forum.dto.TopicoDto;
import com.github.marcoslsouza.api_forum.modelo.Topico;
import com.github.marcoslsouza.api_forum.repository.TopicoRepository;

@RestController
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@RequestMapping("/topicos")
	public List<TopicoDto> lista() {
		List<Topico> topicos = this.topicoRepository.findAll();
		return TopicoDto.converter(topicos);
	}
}
