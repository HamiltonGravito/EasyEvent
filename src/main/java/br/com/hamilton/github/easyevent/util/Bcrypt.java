package br.com.hamilton.github.easyevent.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {

	public static String getHash(String senha) {
		if(senha == null) {
			return null;
		}else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.encode(senha);
		}
	}
}
