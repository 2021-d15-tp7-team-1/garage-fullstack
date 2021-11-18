package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface CrudRoleRepository extends CrudRepository<Role, Long> {
}
