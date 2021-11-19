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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.enums.ApplicationUserRole;
import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;
import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.repository.CrudFicheRepository;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;


@Controller
@SessionAttributes({"tempFiche", "tempTaches"})
@RequestMapping(value = "/entretien")
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
	
	@ModelAttribute("tempFiche")
	public FicheEntretien getTempFiche() {
		FicheEntretien tempFiche = new FicheEntretien();
		tempFiche.setDateCreation(LocalDate.now());
		return tempFiche;
	}
	
	@ModelAttribute("tempTaches")
	public Set<Tache> getTempTaches() {
		return new HashSet<Tache>();
	}
	
	@GetMapping("/create")
	public String createFiche(
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		tempFiche = getTempFiche();
		
		model.addAttribute("fiche", tempFiche);
		model.addAttribute("clients", cr.findAll());
		model.addAttribute("taches", tempTaches);
		model.addAttribute("nouvelleFicheEntretien", tempFiche);
		
		return "fiche_entretien/creation_fiche_entretien";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "enregistrer")
	public String createFiche( 
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien f,
			BindingResult result,
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempFiche.setClient(f.getClient());
		tempFiche.setTaches(tempTaches);
		
		FicheEntretien fiche = fr.save(tempFiche);
		
		for (Tache t : tempTaches) {
			t.setFiche(fiche);
			t = tr.save(t);
			for(Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}
		
		tempTaches.clear();
		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "valider")
	public String validateFiche( 
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien f,
			BindingResult result,
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempFiche.setClient(f.getClient());
		tempFiche.setTaches(tempTaches);
		tempFiche.setIsValid(true);
		
		FicheEntretien fiche = fr.save(tempFiche);
		
		for (Tache t : tempTaches) {
			t.setFiche(fiche);
			t = tr.save(t);
			for(Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}
		
		tempTaches.clear();
		
		return "redirect:/home";
	}
	
	@GetMapping("/create/ajout-tache")
	public String addTache(
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien f,
			BindingResult result,
			Model model) {
		
		Set<String> types = new HashSet<String>();
		for(TypeTache v : TypeTache.values()) {
			types.add(v.name());
		}
		
		Set<String> priorites = new HashSet<String>();
		for(PrioriteTache p : PrioriteTache.values()) {
			priorites.add(p.name());
		}
		
		model.addAttribute("types", types);
		model.addAttribute("mecanos", ur.findByRoleName(ApplicationUserRole.MECANICIEN.name()));
		model.addAttribute("priorites", priorites);
		model.addAttribute("pieces", pr.findAll());
		model.addAttribute("nouvelleTache", new Tache());
		
		return "fiche_entretien/ajout_tache";
	}
	
	@PostMapping("/create/ajout-tache")
	public String addTache(
			@ModelAttribute("nouvelleTache") @Valid Tache t,
			BindingResult result,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {

		tempTaches.add(t);
		
		return "redirect:/entretien/create/";
	}
	
	@GetMapping("create/abort")
	public String abortCreation(
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempTaches.clear();
		
		return "redirect:/home";
	}

}
