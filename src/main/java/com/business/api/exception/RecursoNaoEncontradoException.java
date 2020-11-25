package com.business.api.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Class<?> modelError;
	
	public RecursoNaoEncontradoException(Class<?> model) {
		this.modelError = model;
	}

	public Class<?> getModelError() {
		return modelError;
	}

	public void setModelError(Class<?> model) {
		this.modelError = model;
	}
}
