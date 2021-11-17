package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.FicheEntretien;
import org.springframework.data.repository.CrudRepository;

public interface CrudFicheRepository extends CrudRepository<FicheEntretien, Long> {
}
