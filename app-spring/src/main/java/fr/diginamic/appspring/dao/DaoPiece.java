package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.repository.CrudPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoPiece implements ICrud<Piece>{
    @Autowired
    CrudPieceRepository crud;

    @Override
    public void add(Piece o) {
        crud.save(o);
        System.out.println("Ajout piece avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Piece o) {
        if(o != null){
            crud.deleteById(o.getId());
        }
    }

    @Override
    public void update(Piece o) {
        crud.save(o);
    }

    @Override
    public Piece selectOne(long id) {
        return crud.findById(id).get();
    }

    @Override
    public List<Piece> selectAll() {
        List<Piece> pieces = (List<Piece>) crud.findAll();
        return pieces;
    }

    @Override
    public void deleteAll() {
        crud.deleteAll();
    }
}
