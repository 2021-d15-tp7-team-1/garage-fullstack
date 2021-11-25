package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@ModelAttribute("toutesTaches")
	public List<Set<Tache>> getToutesTaches() {
		return new ArrayList<Set<Tache>>();
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
		
		for (Tache t : fiche.getTaches()) {
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

	@GetMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("toutesTaches") List<Set<Tache>> toutesTaches,
			Model model){
		
		FicheEntretien ficheToUpdate = fr.findById(id).get();
		for(Tache t : ficheToUpdate.getTaches()) {
			tempExistingTaches.add(t);
		}
		
		toutesTaches.add(tempExistingTaches);
		toutesTaches.add(tempTaches);
		
		System.err.println("GET toutesTaches : " + toutesTaches.size());
		System.err.println("GET tempExistingTaches : " + tempExistingTaches.size());
		System.err.println("GET tempTaches : " + tempTaches.size());
		
		model.addAttribute("ficheToUpdate", ficheToUpdate);
		model.addAttribute("tempExistingTaches", tempExistingTaches);
		model.addAttribute("tempTaches", tempTaches);
		model.addAttribute("toutesTaches", toutesTaches);
		model.addAttribute("titre", "MODIFICATION DE FICHE");

		return "fiche_entretien/modification_fiche";
	}

	@PostMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id, 
			@ModelAttribute("ficheToUpdate") @Valid FicheEntretien ficheToUpdate,
			BindingResult result,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			@ModelAttribute("toutesTaches") List<Set<Tache>> toutesTaches){
		
		FicheEntretien ficheEnBase = fr.findById(id).get();
		ficheEnBase.setTaches(null);
		
		System.err.println("POST toutesTaches : " + toutesTaches.size());
		System.err.println("POST tempExistingTaches : " + tempExistingTaches.size());
		System.err.println("POST tempTaches : " + tempTaches.size());
		
		for(Tache t : toutesTaches.get(0)) {
			ficheEnBase.ajouterTache(t);
		}
		
		for(Tache t : toutesTaches.get(1)) {
			t.setId(0);
			ficheEnBase.ajouterTache(t);
		}
		
		FicheEntretien fiche = fr.save(ficheEnBase);
		
		for (Tache t : fiche.getTaches()) {
			t.setFiche(fiche);
			t = tr.save(t);
			for(Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}
		
		toutesTaches.clear();
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

}
