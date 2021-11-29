package fr.diginamic.appspring.authentication;

import java.util.Set;
import java.util.stream.Collectors;

import fr.diginamic.appspring.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.diginamic.appspring.entities.User;

/**
 * Ce service permet de récupérer un user à partir de son username
 * (en vérifiant automatiquement la bonne authentification de l'utilisateur via couple username/mot de passe))
 * 
 * Une fois le user récupéré, on lui adjoint les rôles qui lui sont dévolus.
 * 
 * @return un ApplicationUser, composé notamment du user et de ses rôles.
 * @author vincent
 *
 */
@Service
public class ApplicationUserService implements UserDetailsService {
	
	@Autowired
	private CrudUserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ur.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Username %s not found.", username));
		}
		
		Set<SimpleGrantedAuthority> permissions = user.getUserRoles()
				.stream()
				.map(p -> new SimpleGrantedAuthority(p.getNomRole()))
				.collect(Collectors.toSet());
		
		return new ApplicationUser(user, permissions, true, true, true);
	}

}
