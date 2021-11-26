package fr.diginamic.appspring.controllersRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.repository.CrudPieceRepository;

@RestController
@RequestMapping("rest/pieces")
public class PieceRestController {
	
	@Autowired
	CrudPieceRepository pr;
	
	@GetMapping("/all")
	public List<Piece> findAll() {
		return (List<Piece>) pr.findAll();
	}
}
