package fr.diginamic.appspring;

import fr.diginamic.appspring.dao.ICrud;
import fr.diginamic.appspring.entities.*;
import fr.diginamic.appspring.enums.EtatVehicule;
import fr.diginamic.appspring.enums.TypePiece;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

@SpringBootTest
public class DataBaseTests {

    @Autowired
    ICrud<Role> daoRole;

    @Autowired
    ICrud<User> daoUser;

    @Autowired
    ICrud<Vehicule> daoVehicule;

    @Autowired
    ICrud<Piece> daoPiece;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    //@Before
    @Test
    @Transactional
    void clearDatabase(){
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
    }

    @Test
    void roleInsertion() {
        Role admin = new Role("admin");
        daoRole.add(admin);
        Role chefAtelier = new Role("chef_atelier");
        daoRole.add(chefAtelier);
        Role magasinier = new Role("magasinier");
        daoRole.add(magasinier);
        Role mecanicien = new Role("mécanicien");
        daoRole.add(mecanicien);
        Role commercial = new Role("commercial");
        daoRole.add(commercial);
    }

    @Test
    void userInsertion() {
        User user1 = new User("jdoe", "azerty", "jdoe@mygarage.com", "Doe", "John");
        daoUser.add(user1);
        user1.addRole(daoRole.selectOne(1)); //donne le role admin

        User user2 = new User("mdurand", "zqsd", "mdurand@mygarage.com", "Durand", "Marie");
        daoUser.add(user2);
        user2.addRole(daoRole.selectOne(2)); //donne le role magasinier

        User chefMeca = new User("pmartin", "123", "pmartin@mygarage.com", "Martin", "Pierre");
        daoUser.add(chefMeca);
        chefMeca.addRole(daoRole.selectOne(3)); //donne le role chef_atelier
        chefMeca.addRole(daoRole.selectOne(4)); //donne le role mecanicien

        User commercial = new User("glopez", "abcd", "glopez@mygarage.com", "Lopez", "Gabriela");
        daoUser.add(commercial);
        commercial.addRole(daoRole.selectOne(5)); //donne le role commercial

        User superUser = new User("emusk", "spacex", "emusk@mygarage.com", "Musk", "Elon");
        daoUser.add(superUser);
        for(int i=1; i<=5; i++){ //donne tous les roles
            superUser.addRole(daoRole.selectOne(i));
        }






    }

    @Test
    void vehiculeInsertion(){
        Vehicule v = new Vehicule(1, 12000, 14800,"Twingo", "Renault", "bleu", EtatVehicule.NEUF);
        daoVehicule.add(v);

        v = new Vehicule(1, 15000, 17000,"A1", "Audi", "blanc", EtatVehicule.OCCASION);
        daoVehicule.add(v);

        v = new Vehicule(3, 18000, 20000,"Yaris", "Toyota", "gris", EtatVehicule.NEUF);
        daoVehicule.add(v);

        v = new Vehicule(1, 65000, 72000,"Cayman", "Porshe", "noir", EtatVehicule.NEUF);
        daoVehicule.add(v);
    }

    @Test
    void pieceInsertion(){
        Piece p = new Piece(1, 100, 140, "Carburateur", TypePiece.PIECE);
        daoPiece.add(p);

        p = new Piece(5, 10, 13, "Clé à molette", TypePiece.ARTICLE);
        daoPiece.add(p);

        p = new Piece(0, 800, 1200, "Boite de vitesse", TypePiece.PIECE);
        daoPiece.add(p);

        p = new Piece(25, 15, 20, "Huile moteur 1L", TypePiece.ARTICLE);
        daoPiece.add(p);
    }


}
