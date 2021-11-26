package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;


import fr.diginamic.appspring.dao.DaoFicheEntretien;

import fr.diginamic.appspring.entities.Facture;
import fr.diginamic.appspring.enums.TypeFacture;
import fr.diginamic.appspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;


/**
 * Controller MVC pour gérer les fiches d'entretien
 */
@Controller
@SessionAttributes({"tempFiche", "tempTaches", "tempExistingTaches"})
@RequestMapping(value = "/entretien")
public class FicheEntretienController {
	
	@Autowired
	CrudFicheRepository fr;

	@Autowired
	CrudFactureRepository fa;

	@Autowired
	CrudClientRepository cr;
	
	@Autowired
	CrudTacheRepository tr;
	
	@Autowired
	CrudPieceRepository pr;
	
	@Autowired
	DaoFicheEntretien daoFiche;

	
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

	@ModelAttribute("tempExistingTaches")
	public Set<Tache> getTempExistingTaches() {
		return new HashSet<Tache>();
	}

	/**
	 * Récupère toutes les fiches d'entretien en base,
	 * les transmets au modèle et les affiche
	 * @param model
	 * @return le template thymeleaf qui affiche la liste des fiches
	 */
	@GetMapping("/list")
	public String findAll(Model model){
		model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
		model.addAttribute("titre", "Fiches d'entretien");
		return "fiche_entretien/liste-fiches";
	}

	/**
	 * Récupère une fiche d'entretien via son id et affiche son détail
	 * @param id
	 * @param model
	 * @return le template thymeleaf qui détaille un fiche
	 */
	@GetMapping("/{id}")
	public String afficherFiche(@PathVariable("id") Long id, Model model){
		model.addAttribute("fiche", fr.findById(id).get());
		return "fiche_entretien/detail-fiche";
	}
	
	@GetMapping("/create-init")
	public String createFicheWithoutTache(
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempTaches.clear();
		
		return "redirect:/entretien/create";
	}

	/**
	 * Récupère et affiche le formulaire de création de fiche
	 * @param tempFiche
	 * @param tempTaches
	 * @param model
	 * @return le template thymeleaf du formulaire de création
	 */
	@GetMapping("/create")
	public String createFiche(
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		tempFiche = getTempFiche();
		
		List<Tache> lstTaches = sortTacheCollectionById(tempTaches);
		
		model.addAttribute("fiche", tempFiche);
		model.addAttribute("taches", lstTaches);
		model.addAttribute("nouvelleFicheEntretien", tempFiche);
		model.addAttribute("titre", "CRÉATION DE FICHE");
		
		return "fiche_entretien/creation_fiche_entretien";
	}

	/**
	 * Traite les données du formulaire de création de fiche en envoie la fiche en base
	 * @param f
	 * @param result
	 * @param tempFiche
	 * @param tempTaches
	 * @param model
	 * @return redirige vers la page de liste des fiches
	 */
	@PostMapping("/create")
	public String createFiche( 
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien f,
			BindingResult result,
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("fiche", tempFiche);
			model.addAttribute("taches", tempTaches);
			return "fiche_entretien/creation_fiche_entretien";
		}
		
		tempFiche.setClient(f.getClient());
		
		for(Tache t : tempTaches) {
			t.setId(0);
		}
		tempFiche.setTaches(tempTaches);
		
		FicheEntretien fiche = fr.save(tempFiche);
		
		for(Tache t : fiche.getTaches()) {
			t.setFiche(fiche);
			t = tr.save(t);
			for(Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}
		
		tempTaches.clear();
		
		return "redirect:/entretien/list";
	}

	@GetMapping("/modification-fiche-init/{id}")
	public String updateFicheInit(
			@PathVariable("id") Long id,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches) {
		
		tempExistingTaches.clear();
		
		FicheEntretien ficheToUpdate = fr.findById(id).get();
		
		for(Tache t : ficheToUpdate.getTaches()) {
			tempExistingTaches.add(t);
		}

		return "redirect:/entretien/modification-fiche/"+id;
	}

	/**
	 * Récupère et affiche le formulaire de modification d'une fiche
	 * @param id
	 * @param tempExistingTaches
	 * @param tempTaches
	 * @param model
	 * @return
	 */
	@GetMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model){
		
		FicheEntretien ficheToUpdate = fr.findById(id).get();
		
		model.addAttribute("ficheToUpdate", ficheToUpdate);
		model.addAttribute("tempExistingTaches", tempExistingTaches);
		model.addAttribute("tempTaches", tempTaches);
		model.addAttribute("titre", "MODIFICATION DE FICHE");

		return "fiche_entretien/modification_fiche";
	}

	/**
	 * Traite les modifications d'une fiche d'entretien et les envoie en base de donnée
	 * @param id
	 * @param ficheToUpdate
	 * @param result
	 * @param tempExistingTaches
	 * @param tempTaches
	 * @return
	 */
	@PostMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id, 
			@ModelAttribute("ficheToUpdate") @Valid FicheEntretien ficheToUpdate,
			BindingResult result,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches){
		
		FicheEntretien ficheEnBase = fr.findById(id).get();
		
		
		
		Set<Long> IdsTachesASupprimer = getObjectsIdsToDelete(ficheEnBase.getTaches(), tempExistingTaches);
		
		IdsTachesASupprimer
			.stream()
			.forEach(i -> {
				Tache t = tr.findById(i).get();
				if(t.getPiecesNecessaires().size() > 0) {
					for(Piece p : t.getPiecesNecessaires()) {
						p.getTachesPiece().remove(t);
						pr.save(p);
					}
				}
				t.setPiecesNecessaires(null);
				tr.delete(t);
			});
		
		ficheEnBase.getTaches().clear();
		
		for(Tache t : tempExistingTaches) {
			t.setFiche(ficheEnBase);
			t = tr.save(t);
			ficheEnBase.ajouterTache(t);
		}
		
		for(Tache t : tempTaches) {
			t.setId(0);
			t.setFiche(ficheEnBase);
			t = tr.save(t);
			ficheEnBase.ajouterTache(t);
		}
		
//		ficheEnBase.getClient().getFichesEntretien().remove(ficheEnBase);
//		cr.save(ficheEnBase.getClient());
//		
//		ficheEnBase.setClient(ficheToUpdate.getClient());
//		ficheEnBase.getClient().addFiche(ficheEnBase);
//		cr.save(ficheToUpdate.getClient());
//
//		ficheEnBase = fr.save(ficheEnBase);
//		
//		ficheEnBase.setDateCreation(ficheToUpdate.getDateCreation());
		
		
		for(Tache t : ficheEnBase.getTaches()) {
			for(Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}

		tempExistingTaches.clear();
		tempTaches.clear();

		return "redirect:/entretien/"+id;
	}
	
	@GetMapping("create/abort")
	public String abortCreation(
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempTaches.clear();
		
		return "redirect:/entretien/list";
	}

	/**
	 * Cloture la fiche spécifiée par son id
	 * @param id
	 * @return
	 */
	@GetMapping("/cloturer/{id}")
	public String cloturerFiche(@PathVariable Long id){
		FicheEntretien ficheACloturer = fr.findById(id).get();
		ficheACloturer.cloturerFiche();
		createFactureEntretien(ficheACloturer);
		fr.save(ficheACloturer);

		return "redirect:/entretien/list/";
	}

	/**
	 * Annuler la fiche spécifiée par son id
	 * @param id
	 * @return
	 */
	@GetMapping("/annuler-fiche/{id}")
	public String annulerFiche(@PathVariable Long id){
		FicheEntretien ficheAannuler = fr.findById(id).get();
		ficheAannuler.setValid(false);
		fr.save(ficheAannuler);

		return "redirect:/entretien/list/";
	}

	/**
	 * Trie une collection de tache par leur id
	 * @param collection
	 * @return
	 */
	private List<Tache> sortTacheCollectionById(Set<Tache> collection) {
		List<Tache> lstTaches = new ArrayList<Tache>();
		for(Tache t : collection) {
			lstTaches.add(t);
		}
		
		Collections.sort(lstTaches, new Comparator<Tache>() {
			public int compare(Tache t1, Tache t2) {
				return ((Long) t1.getId()).compareTo((Long) t2.getId());
			}	
		});
		
		return lstTaches;
	}
	
	/**
	 * Compare les ids de deux collections de tâches : 
	 * d'une part les tâches associées à la fiche modifiée
	 * d'autre part les tâches en base associées à la fiche en base destinée à être modifiée
	 * @param tachesEnBase
	 * @param tachesAConserver
	 * @return les ids des tâches à supprimer en base
	 */
	private Set<Long> getObjectsIdsToDelete(Set<Tache> tachesEnBase, Set<Tache> tachesAConserver) {
		Set<Long> idsASupprimer = tachesEnBase
				.stream()
				.map(t -> t.getId())
				.sorted()
				.collect(Collectors.toSet());
			
			Set<Long> idsAConserver = tachesAConserver
				.stream()
				.map(t -> t.getId())
				.sorted()
				.collect(Collectors.toSet());
			
			idsASupprimer.removeAll(idsAConserver);
			return idsASupprimer;
	}

	@GetMapping("facture/{id}")
	public String afficherFacture(@PathVariable("id") Long id, Model model){
		model.addAttribute("facture", fa.findById(id).get());
		return "factures/facture_entretien";
	}

	public void createFactureEntretien(FicheEntretien f) {
			Facture facture = new Facture();
			float sum = 0;
			for (Tache t : f.getTaches()) {
				for (Piece p : t.getPiecesNecessaires()) {
					sum = sum + p.getPrixFacture();
				}
			}
			facture.setPrix(sum);
			facture.setFicheConcernee(f);
			facture.setType(TypeFacture.ENTRETIEN);
			f.setFacture(facture);
			fa.save(facture);
		}
}

