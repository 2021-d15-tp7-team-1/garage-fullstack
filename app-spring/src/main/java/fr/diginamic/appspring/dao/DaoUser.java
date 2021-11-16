package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoUser implements ICrud<User>{
    @PersistenceContext(type = PersistenceContextType.TRANSACTION )
    EntityManager em;

    @Override
    public void add(User o) {
        em.persist(o);
        System.out.println("Ajout user avec l\'id=" + o.getId());
    }

    @Override
    public void delete(User o) {
        User userToDelete = em.find(User.class, o.getId());
        if(userToDelete != null){
            em.remove(userToDelete);
        }
    }

    @Override
    public void update(User o) {
        em.merge(o);
    }

    @Override
    public User selectOne(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> selectAll() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        return users;
    }
}
