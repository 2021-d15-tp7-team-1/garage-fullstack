package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.repository.CrudRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class DaoRole implements ICrud<Role>{

    @Autowired
    CrudRoleRepository em;

    @Override
    public void add(Role r) {
        em.save(r);
        System.out.println("Ajout role avec l\'id=" + r.getId());
    }

    @Override
    public void delete(Role r) {
        if(r != null){
            em.deleteById(r.getId());
        }
    }

    @Override
    public void update(Role o) {
        em.save(o);
    }

    @Override
    public Role selectOne(long id) {
        return em.findById(id).get();
    }

    @Override
    public List<Role> selectAll() {
        List<Role> roles = (List<Role>) em.findAll();
        return roles;
    }

    @Override
    public void deleteAll() {
        em.deleteAll();
    }
}
