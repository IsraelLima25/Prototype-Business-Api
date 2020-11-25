package com.business.api.enums;

import com.business.api.exception.RecursoNaoEncontradoException;

public enum TipoLancamento {

	DESPESA(1, "Despesa"), RECEITA(2, "Receita");

	private Integer cod;
	private String descricao;

	private TipoLancamento(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoLancamento toEnum(String descricao) {

		for (TipoLancamento tipo : TipoLancamento.values()) {
			if (descricao.equals(tipo.getDescricao().toUpperCase())) {
				return tipo;
			}
		}

		throw new RecursoNaoEncontradoException(TipoLancamento.class);
	}
}
