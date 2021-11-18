package com.github.marcoslsouza.api_forum.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.marcoslsouza.api_forum.controller.form.TopicoForm;
import com.github.marcoslsouza.api_forum.dto.DetalhesTopicoDto;
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
	
	// @RequestParam parametros de url
	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, 
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		
		Page<Topico> topicos;
		
		if(nomeCurso == null) {
			topicos = this.topicoRepository.findAll(paginacao);
		} else {
			
			topicos = this.topicoRepository.findByCursoNome(nomeCurso, paginacao);
		}
		
		return topicos.map(TopicoDto::new);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(this.cursoRepository);
		this.topicoRepository.save(topico);
		
		// Retorna o endereço do recurso e um json de topico e status 201 (created)
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		return topico.map(linha -> 
					ResponseEntity.ok().body(new DetalhesTopicoDto(linha))
				).orElse(ResponseEntity.notFound().build()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		
		return topicoRepository.findById(id)
				.map(linha -> {
					
					Topico topico = new Topico();
					topico.setId(id);
					topico.setTitulo(form.getTitulo());
					topico.setMensagem(form.getMensagem());
					topico.setDataCriacao(LocalDateTime.now());
					topico.setStatus(linha.getStatus());
					
					Topico topicoAtualizado = topicoRepository.save(topico);
					return ResponseEntity.ok().body(new TopicoDto(topicoAtualizado));
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		return this.topicoRepository.findById(id)
				.map(linha -> {
					this.topicoRepository.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
}
