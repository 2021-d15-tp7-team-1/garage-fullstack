package fr.diginamic.appspring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.appspring.entities.User;

public interface CrudUserRepo extends CrudRepository<User, Long> {
	
	@Query("select u from User u where u.userName = ?1")
	User findByUsername(String username);

}
