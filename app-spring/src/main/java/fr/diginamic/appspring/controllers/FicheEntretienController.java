package fr.diginamic.appspring.controllers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import fr.diginamic.appspring.dao.DaoFicheEntretien;

import fr.diginamic.appspring.entities.Facture;
import fr.diginamic.appspring.enums.TypeFacture;
import fr.diginamic.appspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Controller
@SessionAttributes({"tempFiche", "tempTaches"})
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

	@GetMapping("/modification-fiche/{id}")
	public String updateFiche(@PathVariable("id") Long id, Model model){
		model.addAttribute("ficheToUpdate", fr.findById(id).get());
		model.addAttribute("titre", "MODIFICATION DE FICHE");

		return "fiche_entretien/modification_fiche";
	}

	@PostMapping("/modification-fiche/{id}")
	public String updateFiche(@PathVariable("id") Long id, @ModelAttribute("ficheUpdated") @Valid FicheEntretien ficheModifiee){
		fr.save(ficheModifiee);

		return "redirect:/entretien/list";
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

