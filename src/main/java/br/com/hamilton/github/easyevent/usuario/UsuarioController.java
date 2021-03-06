package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario){
		Optional<Usuario> usuarioAalterar = service.findById(id);
		Usuario usuarioAlterado = new Usuario();
		if(usuarioAalterar.isPresent()) {
			usuarioAlterado = service.atualizarUsuario(id, usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAlterado);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível alterar este usuário!");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Optional<Usuario> usuarioAdeletar = service.findById(id);
		if(usuarioAdeletar.isPresent()) {
			service.excluirUsuario(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario Não Encontrado!");
		}
	}
}