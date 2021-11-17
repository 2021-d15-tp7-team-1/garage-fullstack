package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.repository.CrudFicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Repository
@Transactional
public class DaoFicheEntretien implements ICrud<FicheEntretien>{
    @Autowired
    CrudFicheRepository crud;

    @Override
    public void add(FicheEntretien o) {
        crud.save(o);
        System.out.println("Ajout fiche avec l\'id=" + o.getId());
    }

    @Override
    public void delete(FicheEntretien o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(FicheEntretien o) {
        crud.save(o);
    }

    @Override
    public FicheEntretien selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<FicheEntretien> selectAll() {
        List<FicheEntretien> taches = (List<FicheEntretien>) crud.findAll();
        return taches;
    }

    @Override
    public void deleteAll() {
        crud.deleteAll();
    }
}
