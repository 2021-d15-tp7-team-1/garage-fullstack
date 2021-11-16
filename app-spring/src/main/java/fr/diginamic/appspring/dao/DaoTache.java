package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Tache;
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
public class DaoTache implements ICrud<Tache>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Tache o) {
        em.persist(o);
        System.out.println("Ajout Tache avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Tache o) {
        Tache tacheToDelete = em.find(Tache.class, o.getId());
        if(tacheToDelete != null){
            em.remove(tacheToDelete);
        }
    }

    @Override
    public void update(Tache o) {
        em.merge(o);
    }

    @Override
    public Tache selectOne(long id) {
        return em.find(Tache.class, id);
    }

    @Override
    public List<Tache> selectAll() {
        List<Tache> taches = em.createQuery("SELECT o FROM Tache o", Tache.class).getResultList();
        return taches;
    }
}