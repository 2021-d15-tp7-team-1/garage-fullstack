package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Tache;
import fr.diginamic.appspring.repository.CrudTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoTache implements ICrud<Tache>{
    @Autowired
    CrudTacheRepository crud;

    @Override
    public void add(Tache o) {
        crud.save(o);
        System.out.println("Ajout Tache avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Tache o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(Tache o) {
        crud.save(o);
    }

    @Override
    public Tache selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<Tache> selectAll() {
        List<Tache> taches = (List<Tache>) crud.findAll();
        return taches;
    }

    @Override
    public void deleteAll() {
        crud.deleteAll();
    }
}