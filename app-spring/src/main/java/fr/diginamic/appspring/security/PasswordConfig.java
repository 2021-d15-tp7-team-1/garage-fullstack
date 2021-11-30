package fr.diginamic.appspring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Encodeur utilisé sur les mots de passe lors de la création, modification des utilisateurs et lors de leur authentification
 * Choix de l'encodage : bcrypt
 * @author vincent
 *
 */
@Configuration
public class PasswordConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
