package br.com.forumexample.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.forumexample.controller.dto.TopicoDto;
import br.com.forumexample.controller.form.TopicoForm;
import br.com.forumexample.model.Topico;
import br.com.forumexample.repository.CursoRepository;
import br.com.forumexample.repository.TopicoRepository;

@RestController/*Utilizando está a anotação, nao é necessario utilizar a anotação responseBody para os metodos*/
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired/*injentando a dependencia repository*/
	private TopicoRepository topicoRepository;
	
	@Autowired/*injentando a dependencia repository*/
	private CursoRepository cursoRepository;
	
	@GetMapping						/*recebendo parametro url*/
	public List<TopicoDto> lista(String nomeCurso) {
		
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();/*metodo que faz a consulta carregando
		 													todos os registros do banco de dados*/
			return TopicoDto.converter(topicos);
		} else {                                    /*padrao de nomenclatura do springdata*/
			List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
		
	}
	/*para pegar o parametro no corpo da requisição*/
	@PostMapping         
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);/*criar um objeto topico, pois estamos recebendo um topicoForm*/
		topicoRepository.save(topico);
		
		//alem de devolver o codigo 201(recurso created), precisa de um cabecalho http com location,
		// e a representacao do recurso
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		                    /*uri do recurso*/  /*leva o topicoDto no corpo da resposta*/
	}
	
	
}
