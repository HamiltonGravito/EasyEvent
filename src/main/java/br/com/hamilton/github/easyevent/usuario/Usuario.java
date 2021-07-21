package br.com.hamilton.github.easyevent.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Data
@JsonInclude(value = Include.NON_NULL)
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", initialValue = 1, allocationSize = 1)
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "usuario_seq")
	@Column(nullable = false, unique = true)
	private Long id;
	
	@NotBlank(message = "O NOME não pode ser um VALOR VAZIO.")
	@Size(min = 3, max = 100, message = "O NOME deve conter no MÍNIMO 3 CARACTERES e no MÁXIMO 100.")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Email(message = "Formato de EMAIL Inválido!")
	@Column(nullable = false,unique = true)
	private String email;
	
	@NotBlank
	@Size(min = 8, message = "A SENHA deve conter no MÍNIMO 8 CARACTERES.")
	@Column(nullable = false)
	private String senha;
}
