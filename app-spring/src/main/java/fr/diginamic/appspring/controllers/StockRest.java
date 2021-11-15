package fr.diginamic.appspring.controllers;

import fr.diginamic.appspring.entities.ElemStock;
import fr.diginamic.appspring.repository.CrudStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockRest {
    @Autowired
    CrudStockRepo cs;

    public StockRest() {
    }


    @GetMapping("/all")
    public List<ElemStock> stockAll() {

        return (List<ElemStock>) cs.findAll();
    }


}
