package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.userName = ?1")
    User findByUsername(String username);
}
