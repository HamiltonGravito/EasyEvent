package br.com.hamilton.github.easyevent.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
	
	@BeforeEach
	public void setUp() {
		System.out.println("###PASSOU PELO SETUP###");
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Teste");
		usuario.setSenha("password");
		
		usuarioRepository.save(usuario);
	}
	
	@Test
	public void testeSalvar() {
		System.out.println("###PASSOU PELO SALVAR###");
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Teste");
		usuario.setSenha("password");
		
		Usuario response = usuarioRepository.save(usuario);
		assertEquals(usuario, response);
	}
	
	@Test
	public void testeBuscarPorId() {
		System.out.println("###PASSOU PELO BUSCAR###");
		Optional<Usuario> response = usuarioRepository.findById(1L);
		assertTrue(response.isPresent());
	}
	
	@AfterEach
	public void tearDown() {
		System.out.println("###PASSOU PELO TEARDOWN###");
		List<Usuario> lista= usuarioRepository.findAll();
		System.out.println(lista.toString());
	}
}
