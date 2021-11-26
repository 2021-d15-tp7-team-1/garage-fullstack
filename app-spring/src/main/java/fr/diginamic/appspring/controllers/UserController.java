package fr.diginamic.appspring.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.entities.User;
import fr.diginamic.appspring.repository.CrudUserRepository;

import fr.diginamic.appspring.repository.CrudRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder pwdEncoder;

    @Autowired
    CrudUserRepository userRepo;

    @Autowired
    CrudRoleRepository roleRepo;

    public UserController() {

    }

    @GetMapping
    public String findall(Model model) {
        model.addAttribute("users", (List<User>) userRepo.findAll());
        model.addAttribute("col", userRepo);
        model.addAttribute("titre", "Liste des collaborateurs");
        return "user/Liste";
    }

    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("collabForm", new User());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("choixRoles", "choixRoles");
        // ajout liste des roles et aficher le libelle et garder en value l'id
        model.addAttribute("titre", "Ajout collaborateurs");
        return "user/add";
    }

    /*
    @PostMapping("/add")
    public String add(Model model, @Valid @ModelAttribute("collabForm") User collabForm) {
        col.save(collabForm); // ajout du nouvel user à la base
        collabForm.getUserRoles().forEach(role -> {
            role.getUsers().add(collabForm);
            roles.save(role); // update de la liste de users de ce role
            System.out.println(role.getNomRole());
        });
        return "redirect:/admin/user";
    }

     */

    @PostMapping("/add")
    public String add(Model model, @Valid @ModelAttribute("collabForm") User collabForm) {
        collabForm.setPassword(pwdEncoder.encode(collabForm.getPassword()));
        userRepo.save(collabForm); //ajout du nouvel user à la base
        collabForm.getUserRoles().forEach(role -> {
            role.getUsers().add(collabForm);
            roleRepo.save(role); //update de la liste de users de ce role
            System.out.println(role.getNomRole());
        });
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long pid) throws Exception {
        Optional<User> c = userRepo.findById(pid);
        if (c.isEmpty()) {
            throw (new Exception("Collaborateurs id : " + pid + "non trouvé !"));
        }

        userRepo.deleteById(pid);
        return "redirect:/admin/user";
    }

    @GetMapping("/modificationUser/{id}")
    public String modifClient(Model model, @PathVariable("id") Long dip) {
        User d = userRepo.findById(dip).get();
        Set<Role> lr = new HashSet <Role>();
        // Obligation dû a des problèmes de droits (priorité entre le Join table de role
        // et celui de User)
        d.getUserRoles().forEach(role -> {
            lr.add(role);
            role.getUsers().remove(d);
            roleRepo.save(role);
        });
        d.getUserRoles().clear();
        userRepo.save(d);
        model.addAttribute("modifUser", d);
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("ancienRoles", lr);
        return "/user/modificationUser";

    }

    @PostMapping("/modificationUser")
    public String modifUser(Model model, @ModelAttribute("modifUser") @Valid User userDip) {

        userDip.getUserRoles().forEach(role -> {
            role.getUsers().add(userDip);
            roleRepo.save(role); // update de la liste de users de ce role
            System.out.println(role.getNomRole());
        });
        userRepo.save(userDip);
        return "redirect:/admin/user";

    }

    @GetMapping("/modificationUser/{id}")
    public String modifClient(Model model, @PathVariable("id") Long dip) {
        User d = col.findById(dip).get();
        Set<Role> lr = new HashSet <Role>();
        // Obligation dû a des problèmes de droits (priorité entre le Join table de role
        // et celui de User)
        d.getUserRoles().forEach(role -> {
            lr.add(role);
            role.getUsers().remove(d);
            roles.save(role);
        });
        d.getUserRoles().clear();
        col.save(d);
        model.addAttribute("modifUser", d);
        model.addAttribute("roles", roles.findAll());
        model.addAttribute("ancienRoles", lr);
        return "/user/modificationUser";

    }

    @PostMapping("/modificationUser")
    public String modifUser(Model model, @ModelAttribute("modifUser") @Valid User userDip) {

        userDip.getUserRoles().forEach(role -> {
            role.getUsers().add(userDip);
            roles.save(role); // update de la liste de users de ce role
            System.out.println(role.getNomRole());
        });
        col.save(userDip);
        return "redirect:/admin/user";

    }
}
