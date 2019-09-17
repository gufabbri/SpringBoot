package br.com.forumexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.forumexample.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);


}
