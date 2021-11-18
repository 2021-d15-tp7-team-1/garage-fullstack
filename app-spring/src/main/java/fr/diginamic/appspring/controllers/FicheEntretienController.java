package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.enums.ApplicationUserRole;
import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;
import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.repository.CrudFicheRepository;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudRoleRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;


@Controller
@RequestMapping("/entretien")
public class FicheEntretienController {
	
	@Autowired
	CrudFicheRepository fr;
	
	@Autowired
	CrudClientRepository cr;
	
	@Autowired
	CrudTacheRepository tr;
	
	@Autowired
	CrudPieceRepository pr;
	
	@Autowired
	CrudUserRepository ur;
	
	@Autowired
	CrudRoleRepository rr;
	
	
	@GetMapping("/create")
	public String addFiche(Model model) {
		
		FicheEntretien fiche = new FicheEntretien();
		fiche.setDateCreation(LocalDate.now());
		fiche.setIsValid(true);
		fiche.setIsCloture(false);
		fiche = fr.save(fiche);
		
		return "redirect:/entretien/create/"+fiche.getId();
	}
	
	@GetMapping("/create/{id}")
	public String createFiche(@PathVariable("id") Long id, Model model) {
		
		FicheEntretien fiche = fr.findById(id).get();
		
		model.addAttribute("fiche", fiche);
		model.addAttribute("clients", cr.findAll());
		model.addAttribute("taches", tr.findByFicheEntretien(fiche));
		model.addAttribute("nouvelleFicheEntretien", fiche);
		
		return "fiche_entretien/creation_fiche_entretien";
	}
	
	@PostMapping("/create/{id}")
	public String createFiche( 
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien f,
			BindingResult result,
			@PathVariable("id") Long id) {
		
		FicheEntretien fiche = fr.findById(id).get();
		fiche.setClient(f.getClient());
		fiche.setDateCreation(f.getDateCreation());
		fr.save(fiche);
		
		return "redirect:/home";
	}
	
	@GetMapping("/create/{id}/ajout-tache")
	public String addTache(@PathVariable("id") Long id, Model model) {
		
		FicheEntretien fiche = fr.findById(id).get();
		
		Set<String> types = new HashSet<String>();
		for(TypeTache v : TypeTache.values()) {
			types.add(v.name());
		}
		
		Set<String> priorites = new HashSet<String>();
		for(PrioriteTache p : PrioriteTache.values()) {
			priorites.add(p.name());
		}
		
		model.addAttribute("fiche", fiche);
		model.addAttribute("types", types);
		model.addAttribute("priorites", priorites);
		model.addAttribute("pieces", pr.findAll());
		model.addAttribute("mecanos", ur.findByRoleName(ApplicationUserRole.MECANICIEN.name()));
		model.addAttribute("nouvelleTache", new Tache());
		
		return "fiche_entretien/ajout_tache";
	}
	
	@PostMapping("/create/{id}/ajout-tache")
	public String addTache(
			@ModelAttribute("nouvelleTache") @Valid Tache t,
			BindingResult result,
			@PathVariable("id") Long id,
			Model model) {
		
		FicheEntretien fiche = fr.findById(id).get();
		Tache nt = new Tache();
		nt.setFiche(fiche);
		nt.setType(t.getType());
		nt.setIntitule(t.getIntitule());
		nt.setPiecesNecessaires(t.getPiecesNecessaires());
		nt.setPriorite(t.getPriorite());
		nt.setMecanicienAttribue(t.getMecanicienAttribue());
		nt.setTerminee(t.isTerminee());
		tr.save(nt);
		
		for(Piece p : nt.getPiecesNecessaires()) {
			p.getTachesPiece().add(nt);
			pr.save(p);
		}
		
		return "redirect:/entretien/create/"+fiche.getId();
	}

}
