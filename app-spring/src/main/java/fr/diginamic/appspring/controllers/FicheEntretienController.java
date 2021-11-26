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

	@GetMapping("/list")
	public String findAll(Model model){
		model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
		model.addAttribute("titre", "Fiches d'entretien");
		return "fiche_entretien/liste-fiches";
	}

	@GetMapping("/{id}")
	public String afficherFiche(@PathVariable("id") Long id, Model model){
		model.addAttribute("fiche", fr.findById(id).get());
		return "fiche_entretien/detail-fiche";
	}
	
	@GetMapping("facture/{id}")
	public String afficherFacture(@PathVariable("id") Long id, Model model){
		model.addAttribute("facture", fa.findById(id).get());
		return "factures/facture_entretien";
	}
	
	@GetMapping("/create-init")
	public String createFicheWithoutTache(
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempTaches.clear();
		
		return "redirect:/entretien/create";
	}
	
	@GetMapping("/create")
	public String createFiche(
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		model.addAttribute("fiche", tempFiche);
		model.addAttribute("taches", sortTacheCollectionById(tempTaches));
		model.addAttribute("nouvelleFicheEntretien", tempFiche);
		model.addAttribute("titre", "CRÉATION DE FICHE");
		
		return "fiche_entretien/creation_fiche_entretien";
	}
	
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
		
		ficheToUpdate.getTaches()
			.stream()
			.forEach(t -> tempExistingTaches.add(t));

		return "redirect:/entretien/modification-fiche/"+id;
	}

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

	@PostMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id, 
			@ModelAttribute("ficheToUpdate") @Valid FicheEntretien ficheToUpdate,
			BindingResult result,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches){
		
		FicheEntretien ficheEnBase = fr.findById(id).get();
		
		deleteObsoleteTasks(ficheEnBase, tempExistingTaches);

		saveActualTasksAndPieces(ficheEnBase, tempExistingTaches, tempTaches);
		
		updateClient(ficheEnBase, ficheToUpdate);

		ficheEnBase.setDateCreation(ficheToUpdate.getDateCreation());
		
		ficheEnBase = fr.save(ficheEnBase);

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

	@GetMapping("/cloturer/{id}")
	public String cloturerFiche(@PathVariable Long id){
		FicheEntretien ficheACloturer = fr.findById(id).get();
		ficheACloturer.cloturerFiche();
		createFactureEntretien(ficheACloturer);
		fr.save(ficheACloturer);

		return "redirect:/entretien/list/";
	}

	@GetMapping("/annuler-fiche/{id}")
	public String annulerFiche(@PathVariable Long id){
		FicheEntretien ficheAannuler = fr.findById(id).get();
		ficheAannuler.setValid(false);
		fr.save(ficheAannuler);

		return "redirect:/entretien/list/";
	}
	
	
	private List<Tache> sortTacheCollectionById(Set<Tache> collection) {
		List<Tache> lstTaches = new ArrayList<Tache>();
//		for(Tache t : collection) {
//			lstTaches.add(t);
//		}
		
		collection.stream().forEach(t -> lstTaches.add(t));
		
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
	
	private void deleteObsoleteTasks(FicheEntretien ficheInBase, Set<Tache> oldTasksToUpdate) {

		getObjectsIdsToDelete(ficheInBase.getTaches(), oldTasksToUpdate)
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
	}
	
	private void saveActualTasksAndPieces(FicheEntretien ficheInBase, Set<Tache> oldTasksToUpdate, Set<Tache> newTasksToAdd) {
//		for(Tache t : ficheInBase.getTaches()) {
//			if(t.getPiecesNecessaires() != null) {
//				for(Piece p : t.getPiecesNecessaires()) {
//					p.getTachesPiece().remove(t);
//					pr.save(p);
//				}
//			}
//		}
		
		ficheInBase.getTaches().stream()
			.filter(t -> t.getPiecesNecessaires() != null)
			.forEach(t -> {
				t.getPiecesNecessaires().stream()
				.forEach(p -> {
					p.getTachesPiece().remove(t);
					pr.save(p);
				});
			});
		
		ficheInBase.getTaches().clear();
		
//		for(Tache t : oldTasksToUpdate) {
//			t.setFiche(ficheInBase);
//			t = tr.save(t);
//			ficheInBase.ajouterTache(t);
//		}
		
		oldTasksToUpdate.stream().forEach(t -> {
			t.setFiche(ficheInBase);
			t = tr.save(t);
			ficheInBase.ajouterTache(t);
		});
			
		
//		for(Tache t : newTasksToAdd) {
//			t.setId(0);
//			t.setFiche(ficheInBase);
//			t = tr.save(t);
//			ficheInBase.ajouterTache(t);
//		}
		
		newTasksToAdd.stream().forEach(t -> {
			t.setId(0);
			t.setFiche(ficheInBase);
			t = tr.save(t);
			ficheInBase.ajouterTache(t);
		});
		
//		for(Tache t : ficheInBase.getTaches()) {
//			if(t.getPiecesNecessaires() != null) {
//				for(Piece p : t.getPiecesNecessaires()) {
//					p.getTachesPiece().add(t);
//					pr.save(p);
//				}
//			}
//		}
		
		ficheInBase.getTaches().stream()
		.filter(t -> t.getPiecesNecessaires() != null)
		.forEach(t -> {
			t.getPiecesNecessaires().stream()
			.forEach(p -> {
				p.getTachesPiece().add(t);
				pr.save(p);
			});
		});	
	}
	
	private void updateClient(FicheEntretien ficheInBase, FicheEntretien modifiedFiche) {		
		if(modifiedFiche.getClient() != null) {
			ficheInBase.getClient().getFichesEntretien().remove(ficheInBase);
			cr.save(ficheInBase.getClient());
			
			ficheInBase.setClient(modifiedFiche.getClient());
			ficheInBase.getClient().addFiche(ficheInBase);
			cr.save(modifiedFiche.getClient());
		}
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

