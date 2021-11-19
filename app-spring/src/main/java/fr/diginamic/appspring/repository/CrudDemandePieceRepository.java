package fr.diginamic.appspring.repository;

import fr.diginamic.appspring.entities.DemandePiece;
import fr.diginamic.appspring.entities.Piece;
import org.springframework.data.repository.CrudRepository;

public interface CrudDemandePieceRepository extends CrudRepository<DemandePiece, Long> {
}
