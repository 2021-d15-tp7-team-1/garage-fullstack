package fr.diginamic.appspring.controllersRest;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import fr.diginamic.appspring.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/taches")
public class TacheRestController {
    @Autowired
    CrudTacheRepository tr;

    @Autowired
    CrudUserRepository ur;

    @GetMapping("/{idMeca}")
    public List<Tache> findByIdMecaAttribue(@PathVariable("idMeca") Long idMeca) {
        return tr.findByIdMecanicien(idMeca);
    }

    @GetMapping("/all")
    public List<Tache> findAll() {
        return (List<Tache>) tr.findAll();
    }

    @GetMapping("/terminer-tache/{id}")
    public String terminerTache(@PathVariable Long id){
        Tache tacheBDD = tr.findById(id).get();
        tacheBDD.setTerminee(true);
        tr.save(tacheBDD);

        return "Tache n°" + id + " est maintenant terminée";
    }
}
