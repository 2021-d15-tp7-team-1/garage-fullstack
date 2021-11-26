package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import fr.diginamic.appspring.dao.DaoFicheEntretien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.repository.CrudFicheRepository;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;


@Controller
@SessionAttributes({"tempFiche", "tempTaches", "tempExistingTaches"})
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
		
		tempFiche = getTempFiche();
		
		List<Tache> lstTaches = sortTacheCollectionById(tempTaches);
		
		model.addAttribute("fiche", tempFiche);
		model.addAttribute("taches", lstTaches);
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
		
		for(Tache t : ficheToUpdate.getTaches()) {
			tempExistingTaches.add(t);
		}

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

	@GetMapping("/cloturer/{id}")
	public String cloturerFiche(@PathVariable Long id){
		FicheEntretien ficheACloturer = fr.findById(id).get();
		ficheACloturer.cloturerFiche();
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

}
