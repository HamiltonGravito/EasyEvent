package br.com.hamilton.github.easyevent.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//Método que busca um usuário pelo seu email (Sintaxe disponibilizada pelo próprio JPA - Método de Consulta por atributos) 
	Optional<Usuario> findByEmail(String email);
}