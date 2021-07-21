package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hamilton.github.easyevent.util.Bcrypt;

@Service
public class UsuarioService implements UsuarioServiceInterface{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public Usuario salvarUsuario(Usuario usuario ) {
		usuario.setSenha(Bcrypt.getHash(usuario.getSenha()));
		Usuario usuarioSalvo = repository.save(usuario);
		usuarioSalvo.setSenha(null);
		return usuarioSalvo;
	}
	
	@Override
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Optional<Usuario> usuarioSalvo = repository.findById(id);
		if(usuarioSalvo.isPresent()) {
			//Copia as propriedades de um objeto para o outro ignorando o "id"
			BeanUtils.copyProperties(usuario, usuarioSalvo.get(), "id");
			salvarUsuario(usuarioSalvo.get());
			return usuarioSalvo.get();
		}else {
			return usuarioSalvo.orElseThrow();
		}	
	}
	
	@Override
	public void excluirUsuario(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	public Optional<Usuario> buscarUsuarioPorEmail(String email) {
		Optional<Usuario> usuarioRecuperado = repository.findByEmail(email);
		return usuarioRecuperado;		
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		Optional<Usuario> usuarioRecuperado = repository.findById(id);
		return usuarioRecuperado;
	}
	
}