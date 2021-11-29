package fr.diginamic.appspring.controllers;

import java.time.LocalDate;
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
 * FicheEntretienController présente toutes les méthodes chargées<br/> 
 * - de la création,<br/>  
 * - de la modification<br/> 
 * - et de la suppression des fiches.
 * @author vincent
 *
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

	@GetMapping("/list")
	public String findAll(Model model) {
		model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
		model.addAttribute("titre", "Fiches d'entretien");
		return "fiche_entretien/liste-fiches";
	}


	/**
	 * getTempFiche() retourne une nouvelle fiche, chargée d'accueillir temporairement des données relatives, pré-enregistrement.<br>
	 * Cette nouvelle fiche est porteuse de la date du jour (date de création proposée par défaut lors de la création d'une nouvelle fiche)
	 * @return FicheEntretien
	 */
	@ModelAttribute("tempFiche")
	public FicheEntretien getTempFiche() {
		FicheEntretien tempFiche = new FicheEntretien();
		tempFiche.setDateCreation(LocalDate.now());
		return tempFiche;
	}
	
	/**
	 * getTempTaches() retourne une nouvelle collection de tâches, destinée à accueillir les nouvelles tâches non encore enregistrées en base.
	 * @return HashSet
	 */
	@ModelAttribute("tempTaches")
	public Set<Tache> getTempTaches() {
		return new HashSet<Tache>();
	}
	
	/**
	 * getTempExistingTaches() retourne une nouvelle collection de tâches, destinée à accueillir les tâches de la fiche récupérées en base.</br>
	 * En cas de modification ou de suppression de ces tâches, celles-ci restent stockées dans la collection avant mise à jour de la fiche d'entretien.
	 * @return HashSet
	 */
	@ModelAttribute("tempExistingTaches")
	public Set<Tache> getTempExistingTaches() {
		return new HashSet<Tache>();
	}
	
	/**
	 * findAll() récupère toutes le fiches enregistrées en base et les transmet au template qui les présente
	 * @param model : le modèle utilisé dans le template
	 * @return String : le chemin du template
	 */
	@GetMapping("/list")
	public String findAll(Model model){
		model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
		model.addAttribute("titre", "Fiches d'entretien");
		return "fiche_entretien/liste-fiches";
	}
	
	/**
	 * afficherFiche() récupère une fiche en fonction de son id et la transmet au template qui en affichera le détail
	 * @param id : l'id de la fiche
	 * @param model : le modèle utilisé dans le template
	 * @return String : le chemin du template
	 */
	@GetMapping("/{id}")
	public String afficherFiche(@PathVariable("id") Long id, Model model){
		model.addAttribute("fiche", fr.findById(id).get());
		return "fiche_entretien/detail-fiche";
	}
	
	/**
	 * afficherFiche() récupère une facture en fonction de son id et la transmet au template qui en affichera le détail
	 * @param id : l'id de la fiche
	 * @param model : le modèle utilisé dans le template
	 * @return String : le chemin du template
	 */
	@GetMapping("facture/{id}")
	public String afficherFacture(@PathVariable("id") Long id, Model model){
		model.addAttribute("facture", fa.findById(id).get());
		return "factures/facture_entretien";
	}
	
	/**
	 * createFicheWithoutTache() est une méthode qui vide les tâches non enregistrées en base qui sont stockées dans leur collection temporaire
	 * @param tempTaches : la collection des nouvelles tâches non encore enregistrées en base
	 * @return String : la redirection vers la méthode de création d'une nouvelle fiche
	 */
	@GetMapping("/create-init")
	public String createFicheWithoutTache(@ModelAttribute("tempTaches") Set<Tache> tempTaches) {

		tempTaches.clear();

		return "redirect:/entretien/create";
	}
	
	/**
	 * createFiche() [GET] récupère<br/>
	 * - la collection des tâches non enregistrées en base<br/>
	 * - la nouvelle fiche<br>
	 * et transmet ces éléments au template de création de fiche, afin qu'ils puissent recueillir les inputs utilisateurs
	 * @param tempFiche : la nouvelle fiche
	 * @param tempTaches : la collection des tâches non enregistrées en base
	 * @param model : le modèle utilisé dans le template
	 * @return String : le chemin du template
	 */
	@GetMapping("/create")
	public String createFiche(
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {
		
		model.addAttribute("fiche", tempFiche);
		model.addAttribute("taches", tempTaches);
		model.addAttribute("nouvelleFicheEntretien", tempFiche);
		model.addAttribute("titre", "CRÉATION DE FICHE");

		return "fiche_entretien/creation_fiche_entretien";
	}
	
	/**
	 * createFiche() [POST] récupère les inputs utilisateurs du formulaire de création, composant un nouvel objet FicheEntretien, afin d'enregistrer cette nouvelle fiche en base.<br/>
	 * Le processus de création d'une fiche tient compte des éventuelles tâches associées à cette fiche et des éventuelles pièces nécessaires aux tâches, ainsi que des relations entre tous ces éléments.  
	 * @param ficheToCreate : la fiche composée des inputs utilisateurs, récupérée depuis le formulaire de création
	 * @param result : l'objet permettant de vérifier la conformité entre les inputs utilisateurs et le type attendu (ici FicheEntretien)
	 * @param tempFiche : la fiche temporaire avant enregistrement en base
	 * @param tempTaches : la collection des tâches non enregistrées en base
	 * @param model : le modèle utilisé dans le template
	 * @return String : la redirection vers la méthode d'affichage de toutes les fiches
	 */
	@PostMapping("/create")
	public String createFiche( 
			@ModelAttribute("nouvelleFicheEntretien") @Valid FicheEntretien ficheToCreate,
			BindingResult result,
			@ModelAttribute("tempsFiche") FicheEntretien tempFiche,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("fiche", tempFiche);
			model.addAttribute("taches", tempTaches);
			return "fiche_entretien/creation_fiche_entretien";
		}
		
		tempTaches.stream().forEach(t -> t.setId(0));
		tempFiche.setTaches(tempTaches);

		tempFiche.setClient(ficheToCreate.getClient());
		
		FicheEntretien fiche = fr.save(tempFiche);
		
		for(Tache t : fiche.getTaches()) {
			t.setFiche(fiche);
			t = tr.save(t);
			for (Piece p : t.getPiecesNecessaires()) {
				p.getTachesPiece().add(t);
				pr.save(p);
			}
		}

		tempTaches.clear();

		return "redirect:/entretien/list";
	}
	
	/**
	 * updateFicheInit() permet, avant d'accéder au formulaire de modification d'une fiche, 
	 * de vider la collection temporaire des tâches déjà enregistrées en base, 
	 * puis de la remplir avec les tâches de la fiche qui sera modifiée. 
	 * @param id : l'id de la fiche à modifier
	 * @param tempExistingTaches : la collection des tâches non enregistrées en base
	 * @return : la redirection vers la méthode de modification de fiche
	 */
	@GetMapping("/modification-fiche-init/{id}")
	public String updateFicheInit(
			@PathVariable("id") Long id,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches) {
		
		FicheEntretien ficheToUpdate = fr.findById(id).get();
		
		tempExistingTaches.clear();
		
		ficheToUpdate.getTaches().stream()
			.forEach(t -> tempExistingTaches.add(t));

		return "redirect:/entretien/modification-fiche/"+id;
	}
	
	/**
	 * updateFiche() [GET] récupère<br/>
	 * - une fiche à partir de son id<br/>
	 * - la collection des tâches déjà enregistrées en base (accueillant les tâches de la fiche récupérée)<br/>
	 * - la collection des nouvelles tâches (vide à cetet étape)<br/>
	 * et transmet ces éléments au template de modification de fiche
	 * @param id : l'id de la fiche
	 * @param tempExistingTaches : la collection des tâches déjà enregistrées en base
	 * @param tempTaches : la collection des nouvelles tâches
	 * @param model : le modèle utilisé dans le template
	 * @return String : le chemin du template
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
	 * updateFiche() [POST] récupère les inputs utilisateurs du formulaire de modification, 
	 * notamment supprime les tâches qui doivent l'être et enregistre les tâches ajoutées par l'utilisateur,
	 * cela en conformité avec les pièces éventuellement nécessaires et les relations liant tous ces éléments.<br>
	 * La fiche est ensuite mise à jour en base.
	 * Les deux collections temporaires de tâches sont vidées à l'issue du processus.
	 * @param id : l'id de la fiche
	 * @param ficheToUpdate : la fiche composée des inputs utilisateurs, récupérée depuis le formulaire de modification
	 * @param result : l'objet permettant de vérifier la conformité entre les inputs utilisateurs et le type attendu (ici FicheEntretien)
	 * @param tempExistingTaches : la collection des tâches déjà enregistrées en base
	 * @param tempTaches : la collection des nouvelles tâches
	 * @return String : la redirection vers la méthode appelant le template de détail d'une fiche
	 */
	@PostMapping("/modification-fiche/{id}")
	public String updateFiche(
			@PathVariable("id") Long id, 
			@ModelAttribute("ficheToUpdate") @Valid FicheEntretien ficheToUpdate,
			BindingResult result,
			@ModelAttribute("tempExistingTaches") Set<Tache> tempExistingTaches,
			@ModelAttribute("tempTaches") Set<Tache> tempTaches){
		
		FicheEntretien ficheEnBase = fr.findById(id).get();
		
		deleteObsoleteTasksAndPiecesRelations(ficheEnBase.getTaches(), tempExistingTaches);

		saveActualTasksAndPiecesRelations(ficheEnBase, tempExistingTaches, tempTaches);
		
		updateClient(ficheEnBase, ficheToUpdate);

		ficheEnBase.setDateCreation(ficheToUpdate.getDateCreation());
		
		ficheEnBase = fr.save(ficheEnBase);

		tempExistingTaches.clear();
		tempTaches.clear();

		return "redirect:/entretien/"+id;
	}
	
	/**
	 * abortCreation() est appelée lors de l'annulation de la création ou de la modification d'une tâche.
	 * La collection temporaire des tâches non encore enregistrées est alors vidée.
	 * @param tempTaches : la collection des nouvelles tâches
	 * @return String : la redirection vers la méthode affichant toutes les fiches enrgistrées en base
	 */
	
	@GetMapping("create/abort")
	public String abortCreation(@ModelAttribute("tempTaches") Set<Tache> tempTaches) {

		tempTaches.clear();

		return "redirect:/entretien/list";
	}
	
	/**
	 * cloturerFiche() récupère une fiche par son id, la clôture et génère la facture correspondant à la fiche.
	 * La mise à jour de la fiche en base est effectuée.
	 * @param id : l'id de la fiche
	 * @return String : la redirection vers la méthode affichant toutes les fiches enrgistrées en base
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
	 * cloturerFiche() récupère une fiche par son id et l'annule.
	 * La mise à jour de la fiche en base est effectuée.
	 * @param id : l'id de la fiche
	 * @return String : la redirection vers la méthode affichant toutes les fiches enrgistrées en base
	 */
	@GetMapping("/annuler-fiche/{id}")
	public String annulerFiche(@PathVariable Long id){
		FicheEntretien ficheAannuler = fr.findById(id).get();
		ficheAannuler.setValid(false);
		fr.save(ficheAannuler);

		return "redirect:/entretien/list/";
	}
	
	
	/**
	 * getObjectsIdsToDelete() compare les ids de deux collections de tâches :<br/>
	 * - d'une part les ids des tâches enregistrées en base (contenant les tâches destinées à être conservées ainsi que celles supprimées par l'utilisateur)<br/>
	 * - d'autre part les ids des tâches de la collection temporaire des tâches enregistrées en base (ne contenant que les tâches destinées à être conservées)<br/>
	 * @param tachesEnBase : la collection des tâches enregistrées en base (toutes les tâches de la fiche qui sont enregistrées en base)
	 * @param tachesAConserver : la collection temporaire des tâches enregistrées en base (uniquement les tâches enregistrées en base et qui ont été conservées par l'utilisateur)
	 * @return idsASupprimer : la collection des ids des tâches à supprimer en base
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
	
	/**
	 * deleteObsoleteTasksAndPiecesRelations() effectue, à partir d'une collection d'ids de tâches obsolète et à supprimer, la suppression des-dites tâches.<br/>
	 * La mise à jour des liens entre ces tâches et les éventuelles pièces qu'elles nécessitent est respectée.
	 * @param ficheInBase : la fiche enregistrée en base dont les tâches doivent être mises à jour
	 * @param oldTasksToUpdate : la collection de 
	 */
	private void deleteObsoleteTasksAndPiecesRelations(Set<Tache> allTasksInBase, Set<Tache> tasksInBaseToUpdate) {
		getObjectsIdsToDelete(allTasksInBase, tasksInBaseToUpdate)
		.stream()
		.forEach(i -> {
			Tache t = tr.findById(i).get();
			if(t.getPiecesNecessaires().size() > 0) {
				t.getPiecesNecessaires().stream()
				.forEach(p -> {
					p.getTachesPiece().remove(t);
					pr.save(p);
				});
			}
			t.setPiecesNecessaires(null);
			tr.delete(t);
		});
	}
	/**
	 * saveActualTasksAndPiecesRelations() effectue l'enregistrement et/ou la mise à jour des tâches d'une fiche, après modification de celle-ci par l'utilisateur
	 * @param ficheInBase : la fiche en base
	 * @param tasksInBaseToUpdate : la collection de tâches déjà en base et mises à jour
	 * @param newTasksToAdd : la collection de nouvelles tâches, non encore enregistrées en base, associées par l'utilisateur
	 */
	private void saveActualTasksAndPiecesRelations(FicheEntretien ficheInBase, Set<Tache> tasksInBaseToUpdate, Set<Tache> newTasksToAdd) {
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
		
		tasksInBaseToUpdate.stream().forEach(t -> {
			t.setFiche(ficheInBase);
			t = tr.save(t);
			ficheInBase.ajouterTache(t);
		});
		
		newTasksToAdd.stream().forEach(t -> {
			t.setId(0);
			t.setFiche(ficheInBase);
			t = tr.save(t);
			ficheInBase.ajouterTache(t);
		});
		
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
	
	/**
	 * updateClient() effectue la mise à jour du client d'une fiche enregistrée en base
	 * @param ficheInBase : la fiche en base
	 * @param modifiedFiche : la fiche récupérée depuis le formulaire de modification de fiche
	 */
	private void updateClient(FicheEntretien ficheInBase, FicheEntretien modifiedFiche) {		
		if(modifiedFiche.getClient() != null) {
			ficheInBase.getClient().getFichesEntretien().remove(ficheInBase);
			cr.save(ficheInBase.getClient());
			
			ficheInBase.setClient(modifiedFiche.getClient());
			ficheInBase.getClient().addFiche(ficheInBase);
			cr.save(modifiedFiche.getClient());
		}
	}
	
	/**
	 * createFactureEntretien() crée une facture depuis un fiche, et l'enregistre eb base
	 * @param f : la fiche donnant lieu à une facture
	 */
	private void createFactureEntretien(FicheEntretien f) {
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

