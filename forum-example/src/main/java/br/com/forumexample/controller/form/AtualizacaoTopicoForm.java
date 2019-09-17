package br.com.forumexample.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.forumexample.model.Topico;
import br.com.forumexample.repository.TopicoRepository;

public class AtualizacaoTopicoForm {

	@NotNull @NotEmpty @Length(min = 4)
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 5)
	private String mensagem;

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

	 /*metodo que recebe o id do topico e o repository(banco de dados), carregar o topico com as informações atuais
	  * sobrescreve com as informações do json e retorna o topico atualizado*/
	
	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getOne(id); /*buscar topico pelo id*/
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topico;
	}
	
	
}
