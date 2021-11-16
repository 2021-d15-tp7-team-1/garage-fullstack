package fr.diginamic.appspring.dao;

import fr.diginamic.appspring.entities.Piece;
import fr.diginamic.appspring.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
@Transactional
public class DaoPiece implements ICrud<Piece>{
    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Piece o) {
        em.persist(o);
        System.out.println("Ajout piece avec l\'id=" + o.getId());
    }

    @Override
    public void delete(Piece o) {
        Piece pieceToDelete = em.find(Piece.class, o.getId());
        if(pieceToDelete != null){
            em.remove(pieceToDelete);
        }
    }

    @Override
    public void update(Piece o) {
        em.merge(o);
    }

    @Override
    public Piece selectOne(long id) {
        return em.find(Piece.class, id);
    }

    @Override
    public List<Piece> selectAll() {
        List<Piece> pieces = em.createQuery("SELECT o FROM Piece o", Piece.class).getResultList();
        return pieces;
    }
}
