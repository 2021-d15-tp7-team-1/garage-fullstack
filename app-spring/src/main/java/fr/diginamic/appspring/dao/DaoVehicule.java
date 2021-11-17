package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Vehicule;
import fr.diginamic.appspring.repository.CrudVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoVehicule implements ICrud<Vehicule>{
    @Autowired
    CrudVehiculeRepository crud;

    @Override
    public void add(Vehicule o) {
        crud.save(o);
        System.out.println("Ajout Vehicule avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Vehicule o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(Vehicule o) {
        crud.save(o);
    }

    @Override
    public Vehicule selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<Vehicule> selectAll() {
        List<Vehicule> vehicules = (List<Vehicule>) crud.findAll();
        return vehicules;
    }

    @Override
    public void deleteAll() {
        crud.deleteAll();
    }
}
