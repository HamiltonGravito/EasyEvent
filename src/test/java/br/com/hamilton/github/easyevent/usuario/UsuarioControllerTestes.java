package br.com.hamilton.github.easyevent.usuario;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

import br.com.hamilton.github.easyevent.util.Bcrypt;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Profile("test")
public class UsuarioControllerTestes {

	@MockBean
	UsuarioService service;
	
	@Autowired
	MockMvc mvc;
	
	private static final Long ID = 1L;
	private static final String NOME = "Controller Teste";
	private static final String EMAIL = "emailtestecontroller@gmail.com";
	private static final String SENHA = "C0ntr0ll&r";
	private static final String URL = "/usuario";
	
	//Esse método envia uma requisição POST para /URL contendo no seu PAYLOAD um USUARIO.
	//Então no UsuarioController existe um POST que recebe um usuario e retorna o status de CREATED junto com o usuario criado.
	@Test
	public void testeSalvarUsuario() throws JsonProcessingException, Exception {
		System.out.println("###SALVAR USUARIO - CONTROLLER###");
		String mock = transformarUsuarioEmStringPayLoad();
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(print());
	}
	
	@Test
	public void testeAtualizarUsuario() throws Exception {
		System.out.println("###ATUALIZAR USUARIO - CONTROLLER###");
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(retornarUsuarioMock()));
		BDDMockito.given(service.atualizarUsuario(Mockito.anyLong(), Mockito.any(Usuario.class))).willReturn(new Usuario(1L, "TesteAtualizar", "emailatualizar@gmail.com", Bcrypt.getHash(SENHA)));
		
		String mock = transformarUsuarioEmStringPayLoad();
		mvc.perform(MockMvcRequestBuilders.put(URL+"/{id}", "1")
				.content(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.nome", is("TesteAtualizar")))
		.andExpect(jsonPath("$.email", is("emailatualizar@gmail.com")))
		.andDo(print());
	}
	
	@Test
	public void testeDeletarUsuario() throws Exception {
		System.out.println("###DELETAR USUARIO - CONTROLLER###");
		//Faz com que exista (MOKA) o usuário com o id que o usuario esta tentando apagar.
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(new Usuario()));
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	public Usuario retornarUsuarioMock() {
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setEmail(EMAIL);
		usuario.setNome(NOME);
		usuario.setSenha(Bcrypt.getHash(SENHA));
		return usuario;
	}
	
	public String transformarUsuarioEmStringPayLoad() throws JsonProcessingException {
		//Transorma um objeto em String JSON
		ObjectMapper mapper = new ObjectMapper();
		String mock = mapper.writeValueAsString(retornarUsuarioMock());
		return mock;
	}
}
