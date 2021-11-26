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

@Controller
@RequestMapping("/clients/listeClients")
public class ClientController {

    @Autowired
    CrudClientRepository cl;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("clients", (List<Client>) cl.findAll());
        model.addAttribute("cl", cl);
        model.addAttribute("titre", "Liste des clients");
        return "clients/listeClients";
    }

    @GetMapping("/add")
    public String addC(Model model) {
        model.addAttribute("creationClient", new Client());
        model.addAttribute("titre", "Ajouter un client");
        return "clients/add";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("creationClient") @Valid Client creationClient) {
        cl.save(creationClient); // ajout d'un nouveau client dans la base
        return "redirect:/clients/listeClients";
    }

    @GetMapping("/accesClient/{id}")
    public String findById(Model model, @PathVariable("id") Long pid) {
        Client c = cl.findById(pid).get();
        model.addAttribute("client", c);
        return "clients/accesClient";
    }

    @GetMapping("/modification/{id}")
    public String modifClient(Model model, @PathVariable("id") Long dip) {
        Client d = cl.findById(dip).get();
        model.addAttribute("modifClient", d);
        return "/clients/modificationClient";

    }

    @PostMapping("/modification")
    public String modifClient(Model model, @ModelAttribute("modifClient") @Valid Client clientDip) {
        cl.save(clientDip);
        return "redirect:/clients/listeClients";

    }

}