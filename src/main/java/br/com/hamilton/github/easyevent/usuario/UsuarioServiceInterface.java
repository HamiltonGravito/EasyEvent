package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

public interface UsuarioServiceInterface {

	//Requisitos Funcionais (O Usuario poderá SALVAR, ATUALIZAR e DELETAR sua conta)
	public Usuario salvarUsuario(Usuario usuario);
	
	public Usuario atualizarUsuario(Long Id, Usuario usuario);
	
	public void excluirUsuario(Long id);
	
	//Métodos para execução dos Requisitos
	public Optional<Usuario> buscarUsuarioPorEmail(String email);
	
	public Optional<Usuario> findById(Long id);
	
}
