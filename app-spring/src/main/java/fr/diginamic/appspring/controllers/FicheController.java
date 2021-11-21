package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.dao.DaoFicheEntretien;
import fr.diginamic.appspring.entities.FicheEntretien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/fiches")
public class FicheController {

    @Autowired
    DaoFicheEntretien daoFiche;

    @GetMapping("/list")
    public String findAll(Model model){
        model.addAttribute("fiches", (List<FicheEntretien>) daoFiche.selectAll());
        model.addAttribute("titre", "Fiches d'entretien");
        return "fiches/liste-fiches";
    }

    @GetMapping("/{id}")
    public String afficherFiche(@PathVariable("id") Long id, Model model){
        model.addAttribute("fiche", daoFiche.selectOne(id));
        return "fiches/detail-fiche";
    }
}
