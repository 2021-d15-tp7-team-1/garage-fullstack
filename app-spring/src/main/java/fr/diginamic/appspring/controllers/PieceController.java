package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller MVC pour gérer les pièces
 */
@Controller
@RequestMapping("/pieces")
public class PieceController {

    @Autowired
    CrudPieceRepository cp;
//    @Autowired
//    private static DaoPiece daoPiece;

    public PieceController() {
    }

    /**
     * Récupère et affiche la liste des pièces
     * @param model
     * @return "pieces/list
     */
    @GetMapping("/list")
    public String pieceList(Model model) {
        model.addAttribute("pieces", (List<Piece>) cp.findAll());
        return "pieces/list";

    }

    /**
     * Récupère et affiche le formulaire d'ajout d'une pièce
     * @param model
     * @return "pieces/add"
     */
    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("pieceForm", new Piece() );
        return "pieces/add";
    }

    /**
     * Traite les données du formulaire d'ajout d'une pièce
     * @param model
     * @param pieceForm
     * @return "redirect:/pieces/list"
     */
    @PostMapping("/add")
    public String add(Model model,
                      @Valid @ModelAttribute("pieceForm") Piece pieceForm)
    {
        cp.save(pieceForm);
        return "redirect:/pieces/list";
    }

    /**
     * Récupère et affiche le formulaire de modification d'une pièce
     * en fonction de son id
     * @param model
     * @param id
     * @return "/pieces/update"
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Piece p = cp.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("piece", p);
        return "pieces/update";
    }

    /**
     * Traite les données du formulaire de modification
     * et enregistre les modification dans la liste des pièces
     * @param model
     * @param id
     * @return"redirect:/pieces/list"
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Piece piece,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            piece.setId(id);
            return "pieces/update";
        }

        cp.save(piece);
        return "redirect:/pieces/list";
    }

    /**
     * Augmente le nombre de pièces en stock
     * @param id
     * @param model
     * @return"redirect:/pieces/list"
     */
    @RequestMapping("/increase/{id}")
    public String increaseAmountOfPieces(@PathVariable("id") long id,
                                                   Model model) {
        Piece piece = cp.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        int qte = piece.getQuantiteStock() + 1;
        piece.setQuantiteStock(qte);
        model.addAttribute("piece",piece);
        cp.save(piece);

        return "redirect:/pieces/list";
    }

    /**
     * Réduit le nombre de pièces en stock
     * @param id
     * @param model
     * @return"redirect:/pieces/list"
     */
    @RequestMapping("/decrease/{id}")
    public String decreaseAmountOfPieces(@PathVariable("id") long id,
                                                   Model model) {
        Piece piece = cp.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (piece.getQuantiteStock() >= 1) {
            int qte = piece.getQuantiteStock() - 1;
            piece.setQuantiteStock(qte);
            model.addAttribute("piece",piece);
            cp.save(piece);
        }
        System.out.println("Quantité nulle");

        return "redirect:/pieces/list";
    }

}