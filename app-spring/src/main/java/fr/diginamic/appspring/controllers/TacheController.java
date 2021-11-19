package fr.diginamic.appspring.controllers;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.enums.ApplicationUserRole;
import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;

@Controller
@SessionAttributes({"tempTaches", "tempTacheId"})
@RequestMapping(value = "/entretien")
public class TacheController {
	
	@Autowired
	CrudPieceRepository pr;
	
	@Autowired
	CrudUserRepository ur;
	
	@Autowired
	CrudTacheRepository tr;
	
	private Long tempId = -1l;
	
	@ModelAttribute("tempTacheId")
	public Long getTempTacheId() {
		return ++tempId;
	}
	
	@GetMapping("/create/ajout-tache")
	public String addTache(Model model) {
		
		model.addAttribute("nouvelleTache", new Tache());
		model.addAttribute("titre", "CRÉATION DE TACHE");

		setTacheFormData(model);
		
		return "tache/ajout_tache";
	}
	
	@PostMapping("/create/ajout-tache")
	public String addTache(
			@ModelAttribute("nouvelleTache") @Valid Tache t,
			BindingResult result,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("tempTacheId") Long id) {
		
		id = getTempTacheId();
		t.setId(id);
		tempTaches.add(t);
		
		return "redirect:/entretien/create/";
	}
	
	@GetMapping("/create/modification-tache/{id}")
	public String updateTache(
			@PathVariable("id") Long id,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		Tache tacheToUpdate = getTacheInCollectionById(tempTaches, id);
		
		model.addAttribute("tacheToUpdate", tacheToUpdate);
		model.addAttribute("titre", "MODIFICATION DE TACHE");

		setTacheFormData(model);
		
		return "tache/modification_tache";
	}
	
	@PostMapping("/create/modification-tache/{id}")
	public String updateTache(
			@PathVariable Long id,
			@ModelAttribute("tacheToUpdate") @Valid Tache tacheModifiee,
			BindingResult result,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		Tache tacheObsolete = getTacheInCollectionById(tempTaches, id);
		
		tempTaches.remove(tacheObsolete);
		tacheModifiee.setId(id);
		tempTaches.add(tacheModifiee);
		
		return "redirect:/entretien/create/";
	}
	
	@GetMapping("/create/suppression-tache/{id}")
	public String deleteTache(
			@PathVariable Long id,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		Tache tacheObsolete = getTacheInCollectionById(tempTaches, id);
		
		tempTaches.remove(tacheObsolete);
		
		return "redirect:/entretien/create/";
	}
	
	
	public void setTacheFormData(Model model) {
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
	}
	
	public Tache getTacheInCollectionById(Set<Tache> collection, Long id) {
		return collection
				.stream()
				.filter(t -> t.getId() == id)
				.findFirst()
				.get();
	}

}
