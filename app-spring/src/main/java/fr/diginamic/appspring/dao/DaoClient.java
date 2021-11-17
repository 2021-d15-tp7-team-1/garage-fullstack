package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.entities.FicheEntretien;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoClient implements ICrud<Client>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Client o) {
        em.persist(o);
        System.out.println("Ajout Client avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Client o) {
        Client clientToDelete = em.find(Client.class, o.getId());
        if(clientToDelete != null){
            em.remove(clientToDelete);
        }
    }

    @Override
    public void update(Client o) {
        em.merge(o);
    }

    @Override
    public Client selectOne(long id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> selectAll() {
        List<Client> clients = em.createQuery("SELECT o FROM Client o", Client.class).getResultList();
        return clients;
    }
}
