package br.com.hamilton.github.easyevent.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.hamilton.github.easyevent.model.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@Profile("test")
public class UsuarioRepositoryTests {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testeSalvar() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Teste");
		usuario.setSenha("password");
		
		Usuario responser = usuarioRepository.save(usuario);
		assertEquals(usuario, responser);
	}
}
