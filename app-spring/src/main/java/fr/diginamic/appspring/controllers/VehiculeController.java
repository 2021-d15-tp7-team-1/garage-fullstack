package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Vehicule;
import fr.diginamic.appspring.repository.CrudVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

/**
 * Controller MVC pour gérer les véhicules
 */
@Controller
@RequestMapping("/vehicules")
public class VehiculeController {
    @Autowired
    CrudVehiculeRepository cs;
//    @Autowired
//    private DaoVehicule daoVehicule;

    public VehiculeController() {
    }

    /**
     * Récupère et affiche la liste des véhicules
     * @param model
     * @return "vehicules/list
     */
    @GetMapping("/list")
    public String vehiculeList(Model model) {
        model.addAttribute("vehicules", (List<Vehicule>) cs.findAll());
        return "vehicules/list";

    }

    /**
     * Récupère et affiche le formulaire d'ajout d'une véhicules
     * @param model
     * @return "vehicules/add"
     */
    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("vehiculeForm", new Vehicule() );
        return "vehicules/add";
    }

    /**
     * Traite les données du formulaire d'ajout d'une véhicules
     * @param model
     * @param pieceForm
     * @return "redirect:/vehicules/list"
     */
    @PostMapping("/add")
    public String add(Model model,
                      @Valid @ModelAttribute("vehiculeForm") Vehicule vehiculeForm)
    {
        cs.save(vehiculeForm);
        return "redirect:/vehicules/list";
    }

    /**
     * Récupère et affiche le formulaire de modification d'un véhicules
     * en fonction de son id
     * @param model
     * @param id
     * @return "/vehicules/update"
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("vehicule", v);
        return "vehicules/update";
    }

    /**
     * Traite les données du formulaire de modification
     * et enregistre les modification dans la liste des véhicules
     * @param model
     * @param id
     * @return"redirect:/vehicules/list"
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Vehicule v,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            v.setId(id);
            return "vehicules/update";
        }

        cs.save(v);
        return "redirect:/vehicules/list";
    }

    /**
     * Augmente le nombre de vehicules en stock
     * @param id
     * @param model
     * @return"redirect:/vehicules/list"
     */
    @RequestMapping("/increase/{id}")
    public String increaseAmountOfVehicules(@PathVariable("id") long id,
                                         Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        int qte = v.getQuantiteStock() + 1;
        v.setQuantiteStock(qte);
        model.addAttribute("v",v);
        cs.save(v);

        return "redirect:/vehicules/list";
    }

    /**
     * Réduit le nombre de vehicules en stock
     * @param id
     * @param model
     * @return"redirect:/vehicules/list"
     */
    @RequestMapping("/decrease/{id}")
    public String decreaseAmountOfVehicules(@PathVariable("id") long id,
                                         Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (v.getQuantiteStock() >= 1) {
            int qte = v.getQuantiteStock() - 1;
            v.setQuantiteStock(qte);
            model.addAttribute("v",v);
            cs.save(v);
        }
        System.out.println("Quantité nulle");

        return "redirect:/vehicules/list";
    }
}