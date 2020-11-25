package com.business.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.api.dto.LancamentoDTO;
import com.business.api.enums.TipoLancamento;
import com.business.api.exception.PessoaInativaException;
import com.business.api.exception.RecursoNaoEncontradoException;
import com.business.api.model.Categoria;
import com.business.api.model.Lancamento;
import com.business.api.model.Pessoa;
import com.business.api.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Lancamento buscarLancamentoPorCodigo(Long codigo) {

		Optional<Lancamento> optionalLancamento = lancamentoRepository.findById(codigo);
		if (optionalLancamento.isPresent()) {
			return optionalLancamento.get();
		}
		throw new RecursoNaoEncontradoException(Lancamento.class);
	}
	
	public Lancamento lancar(LancamentoDTO lancamentoDTO) {
		
		Categoria categoria = categoriaService.buscarCategoriaPorCodigo(lancamentoDTO.getCategoria()
				.getCodigo());
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(lancamentoDTO.getPessoa()
				.getCodigo());
		
		if(!pessoa.isAtivo()) {
			throw new PessoaInativaException();
		}
		
		Lancamento lancamento = new Lancamento();
		
		BeanUtils.copyProperties(lancamentoDTO, lancamento, 
				"codigo", "categoria", "pessoa", "tipo");
		
		lancamento.setCategoria(categoria);
		lancamento.setPessoa(pessoa);
		lancamento.setTipo(TipoLancamento.toEnum(lancamentoDTO.getTipo()));

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		
		return lancamentoSalvo;
	}
	
	public Lancamento atualizar(LancamentoDTO lancamentoDTO, Long codigo) {
		
		Lancamento lancamentoSalvo = buscarLancamentoPorCodigo(codigo);
		
		BeanUtils.copyProperties(lancamentoDTO, lancamentoSalvo, "codigo", "categoria", "pessoa", "tipo");
		
		lancamentoRepository.save(lancamentoSalvo);
		
		return lancamentoSalvo;
	}
	
	public void apagar(Long codigo) {
		Lancamento lancamento = buscarLancamentoPorCodigo(codigo);
		lancamentoRepository.delete(lancamento);
	}
}

