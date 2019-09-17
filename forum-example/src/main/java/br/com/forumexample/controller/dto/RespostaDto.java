package br.com.forumexample.controller.dto;

import java.time.LocalDateTime;

import br.com.forumexample.model.Resposta;

public class RespostaDto {
	
	private Long id;
	private String mensagem;
	private LocalDateTime dataCriação;
	private String nomeAutor;
	
	/*nao precisa dos setter pois no construtor ja recebemos os parametros*/
	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriação = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriação() {
		return dataCriação;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}
	
	
}
