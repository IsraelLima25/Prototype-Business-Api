package com.business.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.api.model.Usuario;
import com.business.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public List<Usuario> listarTodos(){
		return usuarioRepository.findAll();
	}
}
