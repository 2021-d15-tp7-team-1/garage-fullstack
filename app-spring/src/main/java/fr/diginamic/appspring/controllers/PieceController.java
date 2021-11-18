package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
//    @GetMapping("/all")
//    public Iterable<ElemStock> stockAll() {
//        return cs.findAll();
//    }

    //    @PostMapping("/addV")
//    public String vechiculeCreate(@Valid @RequestBody Piece v) {
//        /**
//         * Transactionnel avec commit ou rollback
//         */
//        daoPiece.add(v);//save insert ou update selon l'id !
//        return "Create Ok : " + v.getId();
//    }
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

}