package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.repository.CrudClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoClient implements ICrud<Client>{
    @Autowired
    CrudClientRepository crud;

    @Override
    public void add(Client o) {
        crud.save(o);
        System.out.println("Ajout Client avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Client o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(Client o) {
        crud.save(o);
    }

    @Override
    public Client selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<Client> selectAll() {
        List<Client> clients = (List<Client>) crud.findAll();
        return clients;
    }
}
