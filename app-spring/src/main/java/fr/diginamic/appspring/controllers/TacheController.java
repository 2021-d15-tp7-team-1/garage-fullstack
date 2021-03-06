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

import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.enums.ApplicationUserRole;
import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;

/**
 * Controller MVC pour gérer les taches
 */
@Controller
@SessionAttributes({"tempTaches", "tempTacheId", "tempExistingTaches"})
@RequestMapping(value = "/entretien")
public class TacheController {
	
	@Autowired
	CrudPieceRepository pr;
	
	@Autowired
	CrudUserRepository ur;
	
	@Autowired
	CrudTacheRepository tr;
	
	private Long tempId = 0l;
	
	@ModelAttribute("tempTacheId")
	public Long getTempTacheId() {
		return tempId++;
	}

	/**
	 * Récupère et affiche le formulaire d'ajout de tache depuis la création de fiche
	 * @param model
	 * @return
	 */
	@GetMapping("/create/ajout-tache")
	public String addTache(Model model) {
		
		model.addAttribute("nouvelleTache", new Tache());
		model.addAttribute("creation", true);
		model.addAttribute("titre", "CRÉATION DE TACHE");

		setTacheFormData(model);
		
		return "tache/ajout_tache";
	}

	/**
	 * Traite les données du formulaire d'ajout de tache et ajoute cette tache dans la fiche en cours de création
	 * @param t
	 * @param result
	 * @param tempTaches
	 * @param id
	 * @return
	 */
	@PostMapping("/create/ajout-tache")
	public String addTache(
			@ModelAttribute("nouvelleTache") @Valid Tache t,
			BindingResult result,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("tempTacheId") Long id) {
		
		tempId = tempTaches.size() == 0 ? 1 : tempId;
		id = getTempTacheId();
		t.setId(id);
		tempTaches.add(t);
		
		return "redirect:/entretien/create/";
	}

	/**
	 * Récupère et affiche le formulaire de modification d'une tache d'une fiche en cours de création
	 * @param id
	 * @param tempTaches
	 * @param model
	 * @return
	 */
	@GetMapping("/create/modification-tache/{id}")
	public String updateTache(
			@PathVariable("id") Long id,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		Tache tacheToUpdate = getTacheInCollectionById(tempTaches, id);
		
		model.addAttribute("tacheToUpdate", tacheToUpdate);
		model.addAttribute("isFromCreation", true);
		model.addAttribute("titre", "MODIFICATION DE TACHE");

		setTacheFormData(model);
		
		return "tache/modification_tache";
	}

	/**
	 * Traite les données du formulaire de modification de tache dans la fiche en cours de création
	 * @param id
	 * @param tacheModifiee
	 * @param result
	 * @param tempTaches
	 * @return
	 */
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

	/**
	 * Supprime une tache de la fiche en cours de création
	 * @param id
	 * @param tempTaches
	 * @return
	 */
	@GetMapping("/create/suppression-tache/{id}")
	public String deleteTache(
			@PathVariable Long id,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		Tache tacheObsolete = getTacheInCollectionById(tempTaches, id);
		
		tempTaches.remove(tacheObsolete);
		
		return "redirect:/entretien/create/";
	}

	/**
	 * Récupère et affiche le formulaire d'ajout de tache depuis la modification de la fiche
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/modification-fiche/{id}/ajout-tache")
	public String addTacheToExistingFiche(
			@PathVariable("id") Long id,
			Model model) {
		
		model.addAttribute("nouvelleTache", new Tache());
		model.addAttribute("creation", false);
		model.addAttribute("titre", "CRÉATION DE TACHE");

		setTacheFormData(model);
		
		return "tache/ajout_tache";
	}

	/**
	 * Traite les données du formulaire d'ajout de tache et ajoute cette tache dans la fiche en cours de modification
	 * @param idFiche
	 * @param t
	 * @param result
	 * @param tempTaches
	 * @param idTache
	 * @return
	 */
	@PostMapping("/modification-fiche/{id}/ajout-tache")
	public String addTacheToExistingFiche(
			@PathVariable("id") Long idFiche,
			@ModelAttribute("nouvelleTache") @Valid Tache t,
			BindingResult result,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("tempTacheId") Long idTache) {
		
		tempId = tempTaches.size() == 0 ? 1 : tempId;
		idTache = getTempTacheId();
		t.setId(idTache);
		tempTaches.add(t);
		
		return "redirect:/entretien/modification-fiche/"+idFiche;
	}

	/**
	 * Récupère et affiche le formulaire de modification d'une tache d'une fiche en cours de modification
	 * @param idFiche
	 * @param idTache
	 * @param isInBase
	 * @param tempExistingTaches
	 * @param tempTaches
	 * @param model
	 * @return
	 */
	@GetMapping("/modification-fiche/{idFiche}/modification-tache/{idTache}/{isInBase}")
	public String updateTacheModifFiche(
			@PathVariable("idFiche") Long idFiche,
			@PathVariable("idTache") Long idTache,
			@PathVariable("isInBase") Boolean isInBase,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {

		Tache tacheToUpdate = isInBase == true 
				? getTacheInCollectionById(tempExistingTaches, idTache) 
				: getTacheInCollectionById(tempTaches, idTache);

		model.addAttribute("idFiche", idFiche);
		model.addAttribute("isInBase", isInBase);
		model.addAttribute("tacheToUpdate", tacheToUpdate);
		model.addAttribute("tempExistingTaches", tempExistingTaches);
		model.addAttribute("tempTaches", tempTaches);
		model.addAttribute("isFromCreation", false);
		model.addAttribute("titre", "MODIFICATION DE TACHE");

		setTacheFormData(model);

		return "tache/modification_tache";
	}

	/**
	 * Traite les données du formulaire de modification de tache dans la fiche en cours de modification
	 * @param idFiche
	 * @param idTache
	 * @param isInBase
	 * @param tempExistingTaches
	 * @param tempTaches
	 * @param tacheModifiee
	 * @param result
	 * @return
	 */
	@PostMapping("/modification-fiche/{idFiche}/modification-tache/{idTache}/{isInBase}")
	public String updateTacheModifFiche(
			@PathVariable("idFiche") Long idFiche,
			@PathVariable("idTache") Long idTache,
			@PathVariable("isInBase") Boolean isInBase,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("tacheToUpdate") @Valid Tache tacheModifiee,
			BindingResult result) {

		if(isInBase){
			Tache tacheObsolete = getTacheInCollectionById(tempExistingTaches, idTache);
			tempExistingTaches.remove(tacheObsolete);
			tacheModifiee.setId(idTache);
			tempExistingTaches.add(tacheModifiee);
		}
		else {
			Tache tacheObsolete = getTacheInCollectionById(tempTaches, idTache);
			tempTaches.remove(tacheObsolete);
			tacheModifiee.setId(idTache);
			tempTaches.add(tacheModifiee);
		}

		return "redirect:/entretien/modification-fiche/"+idFiche;
	}

	/**
	 * Supprime une tache de la fiche en cours de modification
	 * @param idFiche
	 * @param idTache
	 * @param isInBase
	 * @param tempExistingTaches
	 * @param tempTaches
	 * @return
	 */
	@GetMapping("/modification-fiche/{idFiche}/suppression-tache/{idTache}/{isInBase}")
	public String deleteTacheFromModifFiche(
			@PathVariable("idFiche") Long idFiche,
			@PathVariable("idTache") Long idTache,
			@PathVariable("isInBase") Boolean isInBase,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {

		if(isInBase){
			Tache tacheObsolete = getTacheInCollectionById(tempExistingTaches, idTache);
			tempExistingTaches.remove(tacheObsolete);
		}
		else {
			Tache tacheObsolete = getTacheInCollectionById(tempTaches, idTache);
			tempTaches.remove(tacheObsolete);
		}

		return "redirect:/entretien/modification-fiche/"+idFiche;
	}
	
	private void setTacheFormData(Model model) {
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
	}
	
	private Tache getTacheInCollectionById(Set<Tache> collection, Long id) {
		return collection
				.stream()
				.filter(t -> t.getId() == id)
				.findFirst()
				.get();
	}

}
