package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

public interface UsuarioServiceInterface {

	public Usuario salvarUsuario(Usuario usuario);
	
	public Optional<Usuario> buscarPorEmail(String email);
	
	public void excluirUsuario(Long id);
	
}
