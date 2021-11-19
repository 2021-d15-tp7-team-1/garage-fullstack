package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.entities.DemandePiece;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.repository.CrudDemandePieceRepository;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/pieces")

public class DemandePieceController {
    @Autowired
    CrudPieceRepository cp;
    @Autowired
    CrudDemandePieceRepository cdp;
//    @Autowired
//    private static DaoPiece daoPiece;

    public DemandePieceController() {
    }

    @GetMapping("/demandes/list")
    public String pieceList(Model model) {
        model.addAttribute("demandes", (List<DemandePiece>) cdp.findAll());
        return "demandes/list";

    }


    @GetMapping("/demandes/add")
    public String addT(Model model) {
        model.addAttribute("pieces", cp.findAll());
        model.addAttribute("dpForm", new DemandePiece() );
        return "/demandes/add";
    }

    @PostMapping("/demandes/add")
    public String add(Model model,
                      @Valid @ModelAttribute("dpForm") DemandePiece dpForm)
    {
    cdp.save(dpForm);
        return "redirect:/demandes/list";
    }

}

