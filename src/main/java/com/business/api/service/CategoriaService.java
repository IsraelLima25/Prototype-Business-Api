package com.business.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.api.exception.RecursoNaoEncontradoException;
import com.business.api.model.Categoria;
import com.business.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarCategoriaPorCodigo(Long codigo) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(codigo);
		
		if(optionalCategoria.isPresent()) {
			return optionalCategoria.get();
		}
		
		throw new RecursoNaoEncontradoException(Categoria.class);
	}
	
	public Categoria atualizar(Categoria categoria, Long codigo) {
		
		Categoria categoriaSalva = buscarCategoriaPorCodigo(codigo);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		
		categoriaRepository.save(categoriaSalva);
		
		return categoriaSalva;
	}
	
	public void apagar(Long codigo) {
		Categoria categoria = buscarCategoriaPorCodigo(codigo);
		categoriaRepository.delete(categoria);
	}
}
