package fr.diginamic.appspring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import fr.diginamic.appspring.authentication.ApplicationUserService;
import fr.diginamic.appspring.enums.ApplicationUserRole;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private PasswordEncoder passwordEncoder;
	private ApplicationUserService applicationUserService;

	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeRequests()
				.antMatchers("/admin/**").hasAuthority(ApplicationUserRole.ADMIN.name())
				.antMatchers("/entretien/create/**").hasAuthority(ApplicationUserRole.CHEF.name())
				.antMatchers("/magasinier/**").hasAuthority(ApplicationUserRole.MAGASINIER.name())
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/home", true)
				.and().logout().clearAuthentication(true).invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "XSRF-TOKEN").logoutSuccessUrl("/login").and().exceptionHandling()
				.accessDeniedPage("/access_denied");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

}
