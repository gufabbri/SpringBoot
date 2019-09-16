package br.com.forumexample.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.forumexample.model.Topico;

public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<TopicoDto> converter(List<Topico> topicos) {
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
		/*rece a lista de topicos e faz a conversao para lista de topicosDto
		 utilizando API java stream
		 fazendo um map de topico para topicoDto
		 com a função chamando o topico como parametro para cada novo objeto topicoDto
		 e transformando para uma lista utilizando collectors*/
	}
	
}
