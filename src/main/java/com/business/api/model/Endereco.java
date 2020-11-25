package com.business.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String logradouro;
	@NotBlank
	private String numero;
	@NotBlank
	private String complemento;
	@NotBlank
	private String bairro;
	@NotBlank
	@Length(min = 8, max = 8)
	private String cep;
	@NotBlank
	private String cidade;
	
	@NotBlank
	@Length(max = 3)
	private String estado;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
