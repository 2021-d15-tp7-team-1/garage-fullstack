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
@Controller
@RequestMapping("/pieces")
public class PieceController {

    @Autowired
    CrudPieceRepository cp;
//    @Autowired
//    private static DaoPiece daoPiece;

    public PieceController() {
    }

    @GetMapping("/list")
    public String pieceList(Model model) {
        model.addAttribute("pieces", (List<Piece>) cp.findAll());
        return "pieces/list";

    }

    @GetMapping("/add")
    public String addT(Model model) {
        model.addAttribute("pieceForm", new Piece() );
        return "pieces/add";
    }

    @PostMapping("/add")
    public String add(Model model,
                      @Valid @ModelAttribute("pieceForm") Piece pieceForm)
    {
        cp.save(pieceForm);
        return "redirect:/pieces/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Piece p = cp.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("piece", p);
        return "pieces/update";
    }

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