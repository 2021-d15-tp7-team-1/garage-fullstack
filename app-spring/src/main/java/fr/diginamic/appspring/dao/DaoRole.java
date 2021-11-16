package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Role;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoRole implements ICrud<Role>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Role r) {
        em.persist(r);
        System.out.println("Ajout role avec l\'id=" + r.getId());
    }

    @Override
    public void delete(Role r) {
        Role roleToDelete = em.find(Role.class, r.getId());
        if(roleToDelete != null){
            em.remove(roleToDelete);
        }
    }

    @Override
    public void update(Role o) {
        em.merge(o);
    }

    @Override
    public Role selectOne(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> selectAll() {
        List<Role> roles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return roles;
    }
}
