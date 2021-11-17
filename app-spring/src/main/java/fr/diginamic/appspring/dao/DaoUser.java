package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.User;
import fr.diginamic.appspring.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoUser implements ICrud<User>{
    @Autowired
    CrudUserRepository crud;

    @Override
    public void add(User o) {
        crud.save(o);
        System.out.println("Ajout user avec l\'id=" + o.getId());
    }

    @Override
    public void delete(User o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(User o) {
        crud.save(o);
    }

    @Override
    public User selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<User> selectAll() {
        List<User> users = (List<User>) crud.findAll();
        return users;
    }

    @Override
    public void deleteAll() {
        crud.deleteAll();
    }
}
