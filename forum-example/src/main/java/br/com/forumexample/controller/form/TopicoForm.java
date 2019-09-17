package br.com.forumexample.controller.form;

import br.com.forumexample.model.Curso;
import br.com.forumexample.model.Topico;
import br.com.forumexample.repository.CursoRepository;

public class TopicoForm {
	
	private String titulo;
	private String mensagem;
	private String nomeCurso;
	
	
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
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Topico converter(CursoRepository cursoRepository) {/*metodo para retornar um objeto topico para cadastro*/
		Curso curso = cursoRepository.findByNome(nomeCurso);/*achar o nome do curso*/
		return new Topico(titulo, mensagem, curso);
		/*como temos so a informação do nome do curso, necessitamos buscar o curto no repository(banco de dados) */
	}

	
	
}
