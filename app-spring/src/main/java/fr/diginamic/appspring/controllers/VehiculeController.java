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

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {
    @Autowired
    CrudVehiculeRepository cs;
//    @Autowired
//    private DaoVehicule daoVehicule;

    public VehiculeController() {
    }

    @GetMapping("/list")
    public String vehiculeList(Model model) {
        model.addAttribute("vehicules", (List<Vehicule>) cs.findAll());
        return "vehicules/list";

    }

    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("vehiculeForm", new Vehicule() );
        return "vehicules/add";
    }

    @PostMapping("/add")
    public String add(Model model,
                      @Valid @ModelAttribute("vehiculeForm") Vehicule vehiculeForm)
    {
        cs.save(vehiculeForm);
        return "redirect:/vehicules/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("vehicule", v);
        return "vehicules/update";
    }

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
     * increaseAmountOfVehicles() récupère un véhicule en base d'après son id et en met à jour la quantité incrémentée (+1).<br/>
     * l'utilisateur est ensuite redirigé vers la liste des véhicules
     * @param id : l'id du véhicule
     * @return String : la redirection vers l'affichage de la liste des véhicules
     */
    @RequestMapping("/increase/{id}")
    public String increaseAmountOfVehicules(@PathVariable("id") long id,
                                         Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
        int qte = v.getQuantiteStock() + 1;
        v.setQuantiteStock(qte);
        model.addAttribute("v",v);
        cs.save(v);

        return "redirect:/vehicules/list";
    }
    
    /**
     * decreaseAmountOfVehicles() récupère un véhicule en base d'après son id et en met à jour la quantité décrémentée (-1).<br/>
     * l'utilisateur est ensuite redirigé vers la liste des véhicules
     * @param id : l'id du véhicule
     * @return String : la redirection vers l'affichage de la liste des véhicules
     */
    @RequestMapping("/decrease/{id}")
    public String decreaseAmountOfVehicules(@PathVariable("id") long id,
                                         Model model) {
        Vehicule v = cs.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
        if (v.getQuantiteStock() >= 1) {
            int qte = v.getQuantiteStock() - 1;
            v.setQuantiteStock(qte);
            model.addAttribute("v",v);
            cs.save(v);
        }
//        System.out.println("Quantité nulle");

        return "redirect:/vehicules/list";
    }
}