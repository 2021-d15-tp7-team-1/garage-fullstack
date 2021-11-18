package fr.diginamic.appspring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.diginamic.appspring.entities.Adresse;
import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.entities.User;
import fr.diginamic.appspring.enums.ApplicationUserRole;
import fr.diginamic.appspring.enums.TypeClient;
import fr.diginamic.appspring.enums.TypePiece;
import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudRoleRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;

@Component
public class Init_db {
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private CrudRoleRepository rr;
	
	@Autowired
	private CrudUserRepository ur;
	
	@Autowired
	private CrudClientRepository cr;
	
	@Autowired
	private CrudPieceRepository pr;
	
	@PostConstruct
	public void init() {
		
		Role admin = new Role(ApplicationUserRole.ADMIN.name());
		rr.save(admin);

		User u1 = new User("a", pwdEncoder.encode("123"), "email", "nom", "prenom");
		ur.save(u1);
		
		u1.getUserRoles().add(admin);
		admin.getUsers().add(u1);
		rr.save(admin);
		ur.save(u1);
		
		Client c1 = new Client(TypeClient.ATELIER, "Proust", "Marcel", "02", "06");
		Client c2 = new Client(TypeClient.ATELIER, "Simenon", "Georges", "02", "06");
		Client c3 = new Client(TypeClient.VENTE, "Houellebecq", "Michel", "02", "06");
		Adresse a1 = new Adresse("1 rue des Mimosas", "Quimper", 29000);
		Adresse a2 = new Adresse("2 rue des Mimosas", "Rennes", 35000);
		Adresse a3 = new Adresse("3 rue des Mimosas", "Nantes", 44000);
		c1.setAdresse(a1);
		c2.setAdresse(a2);
		c3.setAdresse(a3);
		cr.save(c1);
		cr.save(c2);
		cr.save(c3);
		
		Piece volant = new Piece(10, 100, 120, "volant", TypePiece.PIECE);
		Piece frein = new Piece(10, 100, 120, "frein", TypePiece.PIECE);
		Piece pare_brise = new Piece(10, 100, 120, "pare-brise", TypePiece.PIECE);
		Piece huile = new Piece(10, 100, 120, "huile", TypePiece.ARTICLE);
		pr.save(volant);
		pr.save(frein);
		pr.save(pare_brise);
		pr.save(huile);
		
	}
	
}
