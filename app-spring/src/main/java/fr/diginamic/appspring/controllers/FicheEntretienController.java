package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.repository.CrudFicheRepository;
import fr.diginamic.appspring.repository.CrudTacheRepository;

@Controller
@RequestMapping("/entretien")
public class FicheEntretienController {
	
	@Autowired
	CrudFicheRepository fr;
	
	@Autowired
	CrudClientRepository cr;
	
	@Autowired
	CrudTacheRepository tr;
	
	@GetMapping("/create")
	public String add(Model model) {
		
		FicheEntretien fiche = new FicheEntretien();
		fiche.setDateCreation(LocalDate.now());
		fiche.setIsValid(true);
		fiche.setIsCloture(false);
		fiche = fr.save(fiche);
		
		List<Client> clients = (List<Client>) cr.findAll();
		
		model.addAttribute("fiche", fiche);
		model.addAttribute("clients", clients);
		model.addAttribute("dateDuJour", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		model.addAttribute("nouvelleFicheEntretien", new FicheEntretien());
		return "fiche_entretien/creation_fiche_entretien";
	}

}
