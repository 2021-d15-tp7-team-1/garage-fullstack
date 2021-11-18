package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.dao.DaoClient;
import fr.diginamic.appspring.dao.DaoVehicule;
import fr.diginamic.appspring.entities.ElemStock;
import fr.diginamic.appspring.entities.Vehicule;
import fr.diginamic.appspring.enums.EtatVehicule;
import fr.diginamic.appspring.form.VehiculeForm;
import fr.diginamic.appspring.repository.CrudStockRepo;
import fr.diginamic.appspring.repository.CrudVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {
    @Autowired
    CrudVehiculeRepository cs;
//    @Autowired
//    private static DaoVehicule daoVehicule;

    public VehiculeController() {
    }

    @GetMapping("/list")
    public String vehiculeList(Model model) {
        model.addAttribute("vehicules", (List<Vehicule>) cs.findAll());
        return "vehicules/list";

    }
//    @GetMapping("/all")
//    public Iterable<ElemStock> stockAll() {
//        return cs.findAll();
//    }

//    @PostMapping("/addV")
//    public String vechiculeCreate(@Valid @RequestBody Vehicule v) {
//        /**
//         * Transactionnel avec commit ou rollback
//         */
//        daoVehicule.add(v);//save insert ou update selon l'id !
//        return "Create Ok : " + v.getId();
//    }
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

}