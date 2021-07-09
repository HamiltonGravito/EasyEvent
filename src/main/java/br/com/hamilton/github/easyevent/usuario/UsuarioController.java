package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario){
		Optional<Usuario> usuarioAsalvar = service.buscarUsuarioPorEmail(usuario.getEmail());
		if(usuarioAsalvar.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já Cadastrado!");
		}else {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
		}
	}
}