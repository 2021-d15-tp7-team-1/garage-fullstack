package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Tache;
import org.springframework.data.repository.CrudRepository;

public interface CrudTacheRepository extends CrudRepository<Tache, Long> {
}
