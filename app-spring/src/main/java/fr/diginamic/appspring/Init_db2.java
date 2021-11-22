package fr.diginamic.appspring;

import java.util.ArrayList;
import java.util.List;

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
public class Init_db2 {
	
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
	
//	@PostConstruct
	public void init() {
		
		Role admin = new Role(ApplicationUserRole.ADMIN.name());
		Role chef = new Role(ApplicationUserRole.CHEF.name());
		Role magasinier = new Role(ApplicationUserRole.MAGASINIER.name());
		Role mecanicien = new Role(ApplicationUserRole.MECANICIEN.name());
		Role commercial = new Role(ApplicationUserRole.COMMERCIAL.name());
		rr.save(admin);
		rr.save(chef);
		rr.save(magasinier);
		rr.save(mecanicien);
		rr.save(commercial);
		
		List<User> users = new ArrayList<User>();
		users.add(new User("a", pwdEncoder.encode("123"), "email", "admin", "admin"));
		users.add(new User("c", pwdEncoder.encode("123"), "email", "chef", "chef"));
		users.add(new User("maga", pwdEncoder.encode("123"), "email", "magasinier", "magasinier"));
		users.add(new User("m1", pwdEncoder.encode("123"), "email", "mecanicien1", "mecanicien1"));
		users.add(new User("m2", pwdEncoder.encode("123"), "email", "mecanicien2", "mecanicien2"));
		users.add(new User("m3", pwdEncoder.encode("123"), "email", "mecanicien3", "mecanicien3"));
		users.add(new User("m4", pwdEncoder.encode("123"), "email", "mecanicien4", "mecanicien4"));
		users.add(new User("com", pwdEncoder.encode("123"), "email", "commercial", "commercial"));
		ur.saveAll(users);
		
		users.get(0).getUserRoles().add(admin);
		users.get(1).getUserRoles().add(chef);
		users.get(2).getUserRoles().add(magasinier);
		users.get(3).getUserRoles().add(mecanicien);
		users.get(4).getUserRoles().add(mecanicien);
		users.get(5).getUserRoles().add(mecanicien);
		users.get(6).getUserRoles().add(mecanicien);
		users.get(7).getUserRoles().add(commercial);
		ur.saveAll(users);
		
		admin.getUsers().add(users.get(0));
		chef.getUsers().add(users.get(1));
		magasinier.getUsers().add(users.get(2));
		mecanicien.getUsers().add(users.get(3));
		mecanicien.getUsers().add(users.get(4));
		mecanicien.getUsers().add(users.get(5));
		mecanicien.getUsers().add(users.get(6));
		commercial.getUsers().add(users.get(7));
		rr.save(admin);
		rr.save(chef);
		rr.save(magasinier);
		rr.save(mecanicien);
		rr.save(commercial);
		
		Client c1 = new Client(TypeClient.ATELIER, "Proust", "Marcel", "02", "06");
		Client c2 = new Client(TypeClient.ATELIER, "Simenon", "Georges", "02", "06");
		Client c3 = new Client(TypeClient.VENTE, "Houellebecq", "Michel", "02", "06");
		Client c4 = new Client(TypeClient.ATELIER, "Puccini", "Giacomo", "02", "06");
		Client c5 = new Client(TypeClient.ATELIER, "Prokofiev", "Sergei", "02", "06");
		Adresse a1 = new Adresse("1 rue des Mimosas", "Quimper", 29000);
		Adresse a2 = new Adresse("2 rue des Mimosas", "Rennes", 35000);
		Adresse a3 = new Adresse("3 rue des Mimosas", "Nantes", 44000);
		c1.setAdresse(a1);
		c2.setAdresse(a2);
		c3.setAdresse(a3);
		c4.setAdresse(a3);
		c5.setAdresse(a3);
		cr.save(c1);
		cr.save(c2);
		cr.save(c3);
		cr.save(c4);
		cr.save(c5);
		
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
