package fr.diginamic.appspring.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fr.diginamic.appspring.repository.CrudClientRepository;
import fr.diginamic.appspring.entities.Client;

/**
 * Controller MVC pour gérer les clients
 *
 */
@Controller
@RequestMapping("/clients/listeClients")
public class ClientController {

    @Autowired
    CrudClientRepository cl;

    /**
     * Récupère et affiche la liste des clients
     * @param model
     * @return "clients/listeClients"
     * 
     */
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("clients", (List<Client>) cl.findAll());
        model.addAttribute("cl", cl);
        model.addAttribute("titre", "Liste des clients");
        return "clients/listeClients";
    }

    /**
     * Récupère et affiche le formulaire d'ajout d'un nouveau client
     * @param model
     * @return "clients/add"
     */
    @GetMapping("/add")
    public String addC(Model model) {
        model.addAttribute("creationClient", new Client());
        model.addAttribute("titre", "Ajouter un client");
        return "clients/add";
    }

    /**
     * Traite les données du formulaire d'ajout d'un client et l'ajoute à la liste
     * des clients
     * @param model
     * @param creationClient
     * @return "redirect:/clients/listeClients"
     */
    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("creationClient") @Valid Client creationClient) {
        cl.save(creationClient); // ajout d'un nouveau client dans la base
        return "redirect:/clients/listeClients";
    }

    /**
     * Récuèpère et affiche les données d'un client à partir d'un identifiant
     * @param model
     * @param pid
     * @return "clients/accesClient"
     */
    @GetMapping("/accesClient/{id}")
    public String findById(Model model, @PathVariable("id") Long pid) {
        Client c = cl.findById(pid).get();
        model.addAttribute("client", c);
        return "clients/accesClient";
    }

    /**
     * Récupère et affiche le formulaire de modification d'un client en fonction de
     * son id
     * @param model
     * @param dip
     * @return "/clients/modificationClient"
     */
    @GetMapping("/modification/{id}")
    public String modifClient(Model model, @PathVariable("id") Long dip) {
        Client d = cl.findById(dip).get();
        model.addAttribute("modifClient", d);
        return "/clients/modificationClient";

    }

    /**
     * Traite les données de modification du client
     * @param model
     * @param clientDip
     * @return "redirect:/clients/listeClients"
     */
    @PostMapping("/modification")
    public String modifClient(Model model, @ModelAttribute("modifClient") @Valid Client clientDip) {
        cl.save(clientDip);
        return "redirect:/clients/listeClients";

    }

}