package fr.diginamic.appspring.controllers;

import java.util.Optional;
import java.util.List;

import javax.validation.Valid;

import fr.diginamic.appspring.entities.User;
import fr.diginamic.appspring.repository.CrudUserRepository;

import fr.diginamic.appspring.repository.CrudRoleRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    CrudUserRepository col;

    @Autowired

    CrudRoleRepository roles;


    public UserController() {

    }

    @GetMapping
    public String findall(Model model) {
        model.addAttribute("users", (List<User>) col.findAll());
        model.addAttribute("col", col);
        model.addAttribute("titre", "Liste des collaborateurs");
        return "user/Liste";
    }

    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("collabForm", new User());
        model.addAttribute("roles", roles.findAll());
        model.addAttribute("choixRoles", "choixRoles");
        // ajout liste des roles et aficher le libelle et garder en value l'id
        model.addAttribute("titre", "Ajout collaborateurs");
        return "user/add";
    }


    @PostMapping("/add")
    public String add(Model model, @Valid @ModelAttribute("collabForm") User collabForm) {
        // récupérer la selection des rôles et ajouter dans une boucles les rôles
        // (addrole) dans l'objet collabForm
        collabForm.getUserRoles().forEach(role -> {
            collabForm.getUserRoles().add(role);
            col.save(collabForm);
            role.getUsers().add(collabForm);
            roles.save(role);
            System.out.println(role.getNomRole());
        });
        col.save(collabForm);
        return "redirect:/admin/user";
    }

    @PostMapping("/add")
    public String add(Model model, @Valid @ModelAttribute("collabForm") User collabForm) {
        col.save(collabForm); //ajout du nouvel user à la base
        collabForm.getUserRoles().forEach(role -> {
            role.getUsers().add(collabForm);
            roles.save(role); //update de la liste de users de ce role
            System.out.println(role.getNomRole());
        });
        return "redirect:/admin/user";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long pid) throws Exception {
        Optional<User> c = col.findById(pid);
        if (c.isEmpty()) {
            throw (new Exception("Collaborateurs id : " + pid + "non trouvé !"));
        }

        col.deleteById(pid);
        return "redirect:/admin/user";
    }
}
