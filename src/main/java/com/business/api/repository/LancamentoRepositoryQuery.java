package com.business.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.business.api.dto.FiltroLancamentoDTO;
import com.business.api.model.Lancamento;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(FiltroLancamentoDTO filtro, Pageable pageable);

}
