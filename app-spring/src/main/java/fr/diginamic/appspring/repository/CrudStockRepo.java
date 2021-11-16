package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.ElemStock;
import org.springframework.data.repository.CrudRepository;

public interface CrudStockRepo extends CrudRepository<ElemStock, Integer> {
}