package com.business.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.api.exception.RecursoNaoEncontradoException;
import com.business.api.model.Pessoa;
import com.business.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa buscarPessoaPorCodigo(Long codigo) {

		Optional<Pessoa> optionalPessoa = pessoaRepository.findById(codigo);
		if (optionalPessoa.isPresent()) {
			return optionalPessoa.get();
		}
		throw new RecursoNaoEncontradoException(Pessoa.class);
	}

	public Pessoa ativarPessoa(Long codigo) {
		
		Pessoa pessoa = buscarPessoaPorCodigo(codigo);
		if (!pessoa.isAtivo()) {
			pessoa.setAtivo(true);
			Pessoa pessoaAtivada = pessoaRepository.save(pessoa);
			return pessoaAtivada;
		}
		return pessoa;
	}

	public Pessoa desativarPessoa(Long codigo) {
		Pessoa pessoa = buscarPessoaPorCodigo(codigo);
		if (pessoa.isAtivo()) {
			pessoa.setAtivo(false);
			Pessoa pessoaDesativada = pessoaRepository.save(pessoa);
			return pessoaDesativada;
		}
		return pessoa;
	}

	public Pessoa atualizar(Pessoa pessoa, Long codigo) {
		
		Pessoa pessoaSalva = buscarPessoaPorCodigo(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		pessoaRepository.save(pessoaSalva);
		
		return pessoaSalva;
	}

	public void apagar(Long codigo) {
		Pessoa pessoa = buscarPessoaPorCodigo(codigo);
		pessoaRepository.delete(pessoa);
	}
}
