package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Vehicule;
import org.springframework.data.repository.CrudRepository;

public interface CrudVehiculeRepository extends CrudRepository<Vehicule, Long> {
}
