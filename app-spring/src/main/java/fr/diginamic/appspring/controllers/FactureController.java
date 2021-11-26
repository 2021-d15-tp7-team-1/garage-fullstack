package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.dao.DaoFicheEntretien;
import fr.diginamic.appspring.entities.Facture;
import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.enums.TypeFacture;
import fr.diginamic.appspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;


@Controller
@SessionAttributes({"tempFiche", "tempTaches"})
@RequestMapping(value = "/entretien")
public class FactureController {

    @Autowired
    CrudFactureRepository fa;






}

