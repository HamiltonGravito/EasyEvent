package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioServiceInterface{

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario salvarUsuario(Usuario usuario ) {
		Usuario usuarioSalvo = repository.save(usuario);
		return usuarioSalvo;
	}

	public Optional<Usuario> buscarPorEmail(String email) {
		Optional<Usuario> usuarioRecuperado = repository.findByEmail(email);
		return usuarioRecuperado;
	}
	
	public void excluirUsuario(Long id) {
		repository.deleteById(id);
	}
}