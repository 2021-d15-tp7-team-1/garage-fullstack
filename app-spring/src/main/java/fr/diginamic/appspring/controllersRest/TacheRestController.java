package fr.diginamic.appspring.controllersRest;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("rest/taches")
public class TacheRestController {
    @Autowired
    CrudTacheRepository tr;

    @Autowired
    CrudUserRepository ur;

    @CrossOrigin
    @GetMapping("/{idMeca}")
    public List<Tache> findByIdMecaAttribue(@PathVariable("idMeca") Long idMeca) {
        return tr.findByIdMecanicien(idMeca);
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Tache> findAll() {
        return (List<Tache>) tr.findAll();
    }

    @CrossOrigin
    @GetMapping("/terminer-tache/{id}")
    public String terminerTache(@PathVariable Long id){
        Tache tacheBDD = tr.findById(id).get();
        tacheBDD.setTerminee(true);
        tr.save(tacheBDD);

        return "Tache n°" + id + " est maintenant terminée";
    }
}
