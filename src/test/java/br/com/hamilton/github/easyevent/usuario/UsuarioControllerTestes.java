package br.com.hamilton.github.easyevent.usuario;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Profile("test")
public class UsuarioControllerTestes {

	@MockBean
	UsuarioService service;
	
	@Autowired
	MockMvc mvc;
	
	private static final String EMAIL = "emailtestecontroller@gmail.com";
	private static final String NOME = "Controller Teste";
	private static final String SENHA = "C0ntr0ll&r";
	private static final String URL = "/usuario";
	
	
	//Esse método envia uma requisição POST para /URL contendo no seu PAYLOAD um USUARIO
	//Então no UsuarioController existe um POST que recebe um usuario e retorna o status de CREATED
	@Test
	public void testeSalvarUsuario() throws JsonProcessingException, Exception {
		System.out.println("###SALVAR USUARIO - CONTROLLER###");
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(retornarUsuarioMock())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	
	
	public String retornarUsuarioMock() throws JsonProcessingException {
		Usuario usuario = new Usuario();
		usuario.setEmail(EMAIL);
		usuario.setNome(NOME);
		usuario.setSenha(SENHA);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuario);
	}
}
