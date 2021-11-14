package com.github.marcoslsouza.api_forum.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.marcoslsouza.api_forum.modelo.StatusTopico;
import com.github.marcoslsouza.api_forum.modelo.Topico;

public class DetalhesTopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> resposta;
	
	public DetalhesTopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.nomeAutor = (topico.getAutor() != null ? topico.getAutor().getNome() : "");
		this.status = topico.getStatus();
		
		// getRespostas, retorna uma lista de Resposta, e precisamos de uma lista de RespostaDto, por isso usamos o recurso "stream".
		this.resposta = new ArrayList<RespostaDto>();
		this.resposta.addAll(
				topico.getRespostas()
					.stream()
					.map(RespostaDto::new)
					.collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public void setStatus(StatusTopico status) {
		this.status = status;
	}

	public List<RespostaDto> getResposta() {
		return resposta;
	}

	public void setResposta(List<RespostaDto> resposta) {
		this.resposta = resposta;
	}
}
