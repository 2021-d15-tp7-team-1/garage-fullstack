package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.Piece;
import org.springframework.data.repository.CrudRepository;

public interface CrudPieceRepository extends CrudRepository<Piece, Long> {
}
