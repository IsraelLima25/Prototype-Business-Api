package com.business.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.api.event.RecursoCriadoEvent;
import com.business.api.model.Categoria;
import com.business.api.repository.CategoriaRepository;
import com.business.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publish;

	@GetMapping
	public List<Categoria> listarTodos() {
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria,
			HttpServletResponse response){
		
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publish.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo){
		
		Categoria categoriaBusca = categoriaService.buscarCategoriaPorCodigo(codigo);
		
		return ResponseEntity.ok(categoriaBusca);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria, 
			@PathVariable Long codigo){
		Categoria categoriaAtualizada = categoriaService.atualizar(categoria, codigo);
		return ResponseEntity.ok().body(categoriaAtualizada);
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> apagar(@PathVariable Long codigo){
		categoriaService.apagar(codigo);
		return ResponseEntity.noContent().build();
	}
	
}
