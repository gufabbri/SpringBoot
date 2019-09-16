package br.com.forumexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//anotaçao para o spring encontrar a classe
@Controller
public class HelloController {
	
	@RequestMapping("/")/*dizer qual a URL*/
	@ResponseBody /*sem utilizar essa tag, o spring achará que o retorno é uma pagina*/
				  /*Tag para devolver direto ao navegador*/	
	
	public String hello() {
		return "Hello World";
	}

}
