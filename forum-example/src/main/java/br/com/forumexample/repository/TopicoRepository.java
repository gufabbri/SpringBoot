package br.com.forumexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.forumexample.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByCurso_Nome(String nomeCurso);/*monta a query sozinho*/
	
	/*mesma coisa porém nao gera a query sozinho*/
	
//	@Query ("SELECT t FROM topico t WHERE t.curso.nome = :nomeCurso") 
//	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
