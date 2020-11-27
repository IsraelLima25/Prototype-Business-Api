package com.business.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.api.dto.LancamentoDTO;
import com.business.api.event.RecursoCriadoEvent;
import com.business.api.model.Lancamento;
import com.business.api.repository.LancamentoRepository;
import com.business.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publish;
	
	@GetMapping
	public List<Lancamento> listarTodos(){
		return lancamentoRepository.findAll();
	}
	
	@GetMapping(value="/{codigo}")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		
		Lancamento LancamentoBusca = lancamentoService.buscarLancamentoPorCodigo(codigo);
		return ResponseEntity.ok(LancamentoBusca);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> lancar(@Valid @RequestBody LancamentoDTO lancamentoDTO,
			HttpServletResponse response){
		
		Lancamento lancamentoSalvo = lancamentoService.lancar(lancamentoDTO);
		
		publish.publishEvent(new RecursoCriadoEvent(this, response, 
				lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@Valid @RequestBody LancamentoDTO lancamentoDTO, 
			@PathVariable Long codigo){
		Lancamento lancamentoAtualizado = lancamentoService.atualizar(lancamentoDTO, codigo);
		return ResponseEntity.ok().body(lancamentoAtualizado);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> apagar(@PathVariable Long codigo){
		lancamentoService.apagar(codigo);
		return ResponseEntity.noContent().build();
	}
}
