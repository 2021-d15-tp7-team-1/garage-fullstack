package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserRepository extends CrudRepository<User, Long> {

}
