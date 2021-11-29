package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Facture;
import org.springframework.data.repository.CrudRepository;

public interface CrudFactureRepository extends CrudRepository<Facture, Long> {
}
