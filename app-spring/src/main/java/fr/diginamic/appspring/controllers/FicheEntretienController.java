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

	@GetMapping("/list")
	public String findAll(Model model){
		model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
		model.addAttribute("titre", "Fiches d'entretien");
		return "fiche_entretien/liste-fiches";
	}

	@GetMapping("/{id}")
	public String afficherFiche(@PathVariable("id") Long id, Model model){
		model.addAttribute("fiche", daoFiche.selectOne(id));
		return "fiches/detail-fiche";
	}
	
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
		model.addAttribute("taches", tempTaches);
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
		
		return "redirect:/entretien/list";
	}
	
	@GetMapping("create/abort")
	public String abortCreation(
			@ModelAttribute("tempTaches") Set<Tache> tempTaches) {
		
		tempTaches.clear();
		
		return "redirect:/entretien/list";
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
