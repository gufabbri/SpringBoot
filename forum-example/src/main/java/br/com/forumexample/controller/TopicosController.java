package br.com.forumexample.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import br.com.forumexample.controller.dto.DetalhesDoTopicoDto;
import br.com.forumexample.controller.dto.TopicoDto;
import br.com.forumexample.controller.form.AtualizacaoTopicoForm;
import br.com.forumexample.controller.form.TopicoForm;
import br.com.forumexample.model.Topico;
import br.com.forumexample.repository.CursoRepository;
import br.com.forumexample.repository.TopicoRepository;

@RestController /*
				 * Utilizando está a anotação, nao é necessario utilizar a anotação responseBody
				 * para os metodos
				 */
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired /* injentando a dependencia repository */
	private TopicoRepository topicoRepository;

	@Autowired /* injentando a dependencia repository */
	private CursoRepository cursoRepository;

	@GetMapping /* recebendo parametro url */
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, 
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		//Pageable paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
		
		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository
					.findAll(paginacao);/*
								 * metodo que faz a consulta carregando todos os registros do banco de dados
								 */
			return TopicoDto.converter(topicos);
		} else { /* padrao de nomenclatura do springdata */
			Page<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}

	}

	/* para pegar o parametro no corpo da requisição */
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true) /*limpar o cache*/
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form
				.converter(cursoRepository);/* criar um objeto topico, pois estamos recebendo um topicoForm */
		topicoRepository.save(topico);

		// alem de devolver o codigo 201(recurso created), precisa de um cabecalho http
		// com location,
		// e a representacao do recurso

		/* utilizamos o uriBuilder para criar um objeto uri */

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		/* uri do recurso */ /* leva o topicoDto no corpo da resposta */
	}

	/* lógica para detalhar um topico */
	@GetMapping("/{id}") /* anotação para dizer que é uma variavel da url */
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}") /* utilizado para sobrescrever o recurso */
	@Transactional /*avisar que é pra commitar a atualização*/
	@CacheEvict(value = "listaDeTopicos", allEntries = true) /*limpar o cache*/
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();		
		/*está vindo um ID do topico que quero atualizar, carregar ele do banco de dados
		  e depois sobrescrever com as informações que vem no objeto atualizacaoDoTopico */		
		
		   /*classe para montar uma resposta*/
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true) /*limpar o cache*/
	public ResponseEntity<?> remover(@PathVariable Long id) {
		/*tratamento de erro para devolver um erro 404 caso nao exista o id na requisição do cliente*/
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
