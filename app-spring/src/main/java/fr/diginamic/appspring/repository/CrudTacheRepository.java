package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Tache;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CrudTacheRepository extends CrudRepository<Tache, Long> {
	
	@Query("select t from Tache t where t.fiche = ?1")
    List<Tache> findByFicheEntretien(FicheEntretien fiche);

    @Query("select t from Tache t where t.mecanicienAttribue.id = ?1")
    List<Tache> findByIdMecanicien(Long id);
}
