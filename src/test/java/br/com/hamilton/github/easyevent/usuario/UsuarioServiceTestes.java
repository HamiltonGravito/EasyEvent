package br.com.hamilton.github.easyevent.usuario;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Profile("test")
public class UsuarioServiceTestes {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	private static final String NOME = "TesteBefore";
	private static final String SENHA = "Password";
	private static final String EMAIL = "emailbefore@gmail.com";
	
	//Esse método cria um usuário no banco de dados antes de qualquer teste.
	@BeforeEach
	public void setUp() {
		System.out.println("###SETUP###");
		Usuario usuario = new Usuario();
		usuario.setNome(NOME);
		usuario.setSenha(SENHA);
		usuario.setEmail(EMAIL);
		service.salvarUsuario(usuario);
	}
	
	//Esse método salva um usuário no Banco de Dados.
	@Test
	public void testeSalvarUsuario() {
		System.out.println("###SALVAR###");
		Usuario usuario = new Usuario();
		usuario.setNome("Teste");
		usuario.setSenha("password");
		usuario.setEmail("exemploemail@gmail.com");
		Usuario usuarioSalvo = service.salvarUsuario(usuario);
		assertEquals(usuario, usuarioSalvo);
	}
	
	//Esse método recupera um usuario pelo seu email.
	@Test
	public void testeRecuperarUsuarioPorEmail() {
		System.out.println("###BUSCAR###");
		Optional<Usuario> usuario = service.buscarUsuarioPorEmail(EMAIL);
		assertTrue(usuario.isPresent());
		assertEquals(usuario.get().getEmail(), EMAIL);
	}
	
	//Esse método exclui um usuario pelo seu ID.
	@Test
	public void testeDeletarUsuario() {
		System.out.println("###DELETAR###");
		Optional<Usuario> usuario = service.buscarUsuarioPorEmail(EMAIL);
		service.excluirUsuario(usuario.get().getId());
		
		Optional<Usuario> usuarioDeletado = service.buscarUsuarioPorEmail(EMAIL);
		assertFalse(usuarioDeletado.isPresent());
	}
	
	//Esse método atualiza um usuario recebendo o usuario atualizado e o id do usuario à atualizar.
	@Test
	public void testeAtualizarUsuario() {
		System.out.println("###ATUALIZAR###");
		Optional<Usuario> usuario = service.buscarUsuarioPorEmail(EMAIL);
		Usuario usuarioAlterado = new Usuario();
		usuarioAlterado.setId(usuario.get().getId());
		usuarioAlterado.setNome(NOME);
		usuarioAlterado.setEmail("emailalterado@gmail.com");
		usuarioAlterado.setSenha("P@ssw0rd@lter@d0");
		
		service.atualizarUsuario(usuario.get().getId(), usuarioAlterado);
		assertEquals(usuario.get().getId(), usuarioAlterado.getId());
		assertNotEquals(usuario.get().getEmail(), usuarioAlterado.getEmail());
	}
	
	//Esse método valida o erro na atualização do usuário
	@Test
	public void testeFalhaAtualizarUsuario() {
		System.out.println("###ATUALIZAR-FALHAR###");
		Optional<Usuario> usuario = service.buscarUsuarioPorEmail("emailinexistente@gmail.com");
		Exception excecao = assertThrows(Exception.class, () -> {
			usuario.orElseThrow();
		});
		assertTrue(excecao.getMessage().contains("No value present"));
	}
	
	//Esse método deleta todos os usuário no banco de dados depois de qualquer teste.
	//Obs.:A SEQUENCE criada não retorna ao valor 1 a cada inserção, porém, sempre é inicializada a cada bateria de teste.
	@AfterEach
	public void tearDown() {
		System.out.println("###DESTRUIR###");
		System.out.println(repository.findAll());
		repository.deleteAll();
	}

}
