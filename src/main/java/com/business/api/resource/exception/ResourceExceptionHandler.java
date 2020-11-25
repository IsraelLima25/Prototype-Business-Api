package com.business.api.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.business.api.exception.PessoaInativaException;
import com.business.api.exception.RecursoNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> argumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		
		ValidationError err = new ValidationError(System.currentTimeMillis(), 
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro na Validação dos campos", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ValidationError> recursoNaoEncontrado(RecursoNaoEncontradoException e,
			HttpServletRequest request){
				
		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				e.toString(), messageSource.getMessage("registro.nao-encontrado", null,
						LocaleContextHolder.getLocale()), request.getRequestURI());
		
		err.addError(e.getModelError().getName(), messageSource
				.getMessage("campo-invalido", null, LocaleContextHolder.getLocale()));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(PessoaInativaException.class)
	public ResponseEntity<StandarError> pessoaInativa(PessoaInativaException e, HttpServletRequest request){
		
		StandarError standarError = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value() 
				,e.toString(), messageSource.getMessage("pessoa.inexistente", null, LocaleContextHolder.getLocale())
				, request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standarError);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandarError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
		StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				messageSource.getMessage("integridade.dados", null,
						LocaleContextHolder.getLocale()), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
