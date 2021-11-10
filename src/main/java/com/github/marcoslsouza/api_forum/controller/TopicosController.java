package com.github.marcoslsouza.api_forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.api_forum.controller.form.TopicoForm;
import com.github.marcoslsouza.api_forum.dto.TopicoDto;
import com.github.marcoslsouza.api_forum.modelo.Topico;
import com.github.marcoslsouza.api_forum.repository.CursoRepository;
import com.github.marcoslsouza.api_forum.repository.TopicoRepository;

@RestController
@RequestMapping("topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		if(nomeCurso == null) {
			List<Topico> topicos = this.topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			// Monta a querie para pesquisar por titulo automaticamente
			// List<Topico> topicos = this.topicoRepository.findByTitulo();
			
			// Monta a querie para pesquisar por nome do curso automaticamente.
			// Dentro de Topico temos Curso e dentro de curso temos nome. Poderia ser: findByCurso_Nome
			List<Topico> topicos = this.topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
	}
	
	@PostMapping
	public void cadastrar(@RequestBody TopicoForm topicoForm) {
		Topico topico = topicoForm.converter(cursoRepository);
		this.topicoRepository.save(topico);
	}
}
