package br.com.forumexample.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.forumexample.controller.dto.TopicoDto;
import br.com.forumexample.model.Curso;
import br.com.forumexample.model.Topico;

@RestController/*Utilizando está a anotação, nao é necessario utilizar a anotação responseBody para os metodos*/
public class TopicosController {

	@RequestMapping("/topicos")	
	public List<TopicoDto> lista() {
		Topico topico = new Topico("Dúvida", "Duvida com Spring", new Curso("Spring", "Programação"));
		
		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
	}
	
	
}
