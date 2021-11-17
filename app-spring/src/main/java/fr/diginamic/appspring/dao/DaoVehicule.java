package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.Vehicule;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoVehicule implements ICrud<Vehicule>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Vehicule o) {
        em.persist(o);
        System.out.println("Ajout Vehicule avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Vehicule o) {
        Vehicule vehiculeToDelete = em.find(Vehicule.class, o.getId());
        if(vehiculeToDelete != null){
            em.remove(vehiculeToDelete);
        }
    }

    @Override
    public void update(Vehicule o) {
        em.merge(o);
    }

    @Override
    public Vehicule selectOne(long id) {
        return em.find(Vehicule.class, id);
    }

    @Override
    public List<Vehicule> selectAll() {
        List<Vehicule> vehicules = em.createQuery("SELECT o FROM Vehicule o", Vehicule.class).getResultList();
        return vehicules;
    }
}
