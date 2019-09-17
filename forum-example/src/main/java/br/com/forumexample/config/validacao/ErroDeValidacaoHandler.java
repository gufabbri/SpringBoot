package br.com.forumexample.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice /*esse controller advice vai fazer tratamentos de erro*/
public class ErroDeValidacaoHandler {

	/*Spring ajuda com a classe messageSource para pegar mensagem de erros;
	 vamos injetar na classe handler um atributo messageSource*/
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)/*devoler esse status*/
	@ExceptionHandler(MethodArgumentNotValidException.class)/*ser chamado quando houver uma excecao dentro de algum controller*/
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		
				List<ErroDeFormularioDto> dto = new ArrayList<>();
				
				/*para cada objeto fieldErrors criar um objeto errodto e guardar na lista dto*/
				List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
				/*esta variavel contem os erros de formulario, inclusive o nome do campo utilizado abaixo*/
				
				fieldErrors.forEach(e -> {          /*descobre o locale e pega a msg com o idioma correto*/
					String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
					ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
					dto.add(erro);
				});
				
				return dto;
	}
	
}
