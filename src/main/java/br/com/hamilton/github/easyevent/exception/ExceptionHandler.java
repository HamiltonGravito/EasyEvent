package br.com.hamilton.github.easyevent.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 Essa Classe foi criada para tratar os ERROS dos objetos @VALID (Validações no model), nesse contexto é tratada cada exceção pelo seu tipo e enviada uma
 mensagem ao usuario e ao desenvolvedor. OBS.: Foi criado juntamente uma classe ERRO onde as mensagens são distribuidas. 
 **/

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError: bindingResult.getFieldErrors()) {
		String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = fieldError.toString();
		erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		return erros;
	}
	
	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		
		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
	}
}