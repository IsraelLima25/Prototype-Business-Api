package com.business.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.business.api.dto.FiltroLancamentoDTO;
import com.business.api.model.Lancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(FiltroLancamentoDTO lancamentoFiltro, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoFiltro, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		criarRestricoesDePaginacao(query, pageable);		

		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFiltro));
		
	}

	private void criarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalResgistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalResgistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalResgistrosPorPagina);
		
	}
	
	private Long total(FiltroLancamentoDTO lancamentoFiltro) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFiltro, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
		
	}
	
	private Predicate[] criarRestricoes(FiltroLancamentoDTO lancamentoFiltro, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();

		if (lancamentoFiltro.getDescricao() != null) {
			
			predicates.add(builder.like(builder.lower(root.get("descricao")),"%" 
					+ lancamentoFiltro.getDescricao().toLowerCase() + "%"));
		}

		if (lancamentoFiltro.getDataVencimentoDe() != null) {
			
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"),
					lancamentoFiltro.getDataVencimentoDe()));
			
		}

		if (lancamentoFiltro.getDataVencimentoAte() != null) {
			
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"),
					lancamentoFiltro.getDataVencimentoAte()));

		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
