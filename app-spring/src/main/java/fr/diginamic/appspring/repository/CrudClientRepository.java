package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface CrudClientRepository extends CrudRepository<Client, Long> {
}
