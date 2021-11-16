package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.FicheEntretien;
import fr.diginamic.appspring.entities.Tache;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Repository
@Transactional
public class DaoFicheEntretien implements ICrud<FicheEntretien>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(FicheEntretien o) {
        em.persist(o);
        System.out.println("Ajout fiche avec l\'id=" + o.getId());
    }

    @Override
    public void delete(FicheEntretien o) {
        FicheEntretien ficheToDelete = em.find(FicheEntretien.class, o.getId());
        if(ficheToDelete != null){
            em.remove(ficheToDelete);
        }
    }

    @Override
    public void update(FicheEntretien o) {
        em.merge(o);
    }

    @Override
    public FicheEntretien selectOne(long id) {
        return em.find(FicheEntretien.class, id);
    }

    @Override
    public List<FicheEntretien> selectAll() {
        List<FicheEntretien> taches = em.createQuery("SELECT o FROM FicheEntretien o", FicheEntretien.class).getResultList();
        return taches;
    }
}
