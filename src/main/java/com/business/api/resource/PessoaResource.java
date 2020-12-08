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
import com.business.api.model.Pessoa;
import com.business.api.repository.PessoaRepository;
import com.business.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publish;

//	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public List<Pessoa> listarTodos(){
		return pessoaRepository.findAll(); 
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response){

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publish.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		
		Pessoa pessoaBusca = pessoaService.buscarPessoaPorCodigo(codigo);
		return ResponseEntity.ok(pessoaBusca);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/ativar/{codigo}")
	public ResponseEntity<Pessoa> ativar(@PathVariable Long codigo){
		Pessoa pessoaAtivada = pessoaService.ativarPessoa(codigo);		
		return ResponseEntity.ok().body(pessoaAtivada);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/desativar/{codigo}")
	public ResponseEntity<Pessoa> desativar(@PathVariable Long codigo){
		Pessoa pessoaDesativada = pessoaService.desativarPessoa(codigo);		
		return ResponseEntity.ok().body(pessoaDesativada);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@Valid @RequestBody Pessoa pessoa, @PathVariable Long codigo){
		Pessoa pessoaAtualizada = pessoaService.atualizar(pessoa, codigo);
		return ResponseEntity.ok().body(pessoaAtualizada);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> apagar(@PathVariable Long codigo){
		pessoaService.apagar(codigo);
		return ResponseEntity.noContent().build();
	}
}
