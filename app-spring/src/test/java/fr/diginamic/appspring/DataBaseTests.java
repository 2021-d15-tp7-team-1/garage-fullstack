package fr.diginamic.appspring;

import fr.diginamic.appspring.dao.ICrud;
import fr.diginamic.appspring.entities.*;
import fr.diginamic.appspring.enums.EtatVehicule;
import fr.diginamic.appspring.enums.TypePiece;
import fr.diginamic.appspring.enums.TypeTache;
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

    @Autowired
    ICrud<Tache> daoTache;

    @Autowired
    ICrud<FicheEntretien> daoFiche;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    //@Before
    //@Test
    void clearDatabase(){
        daoRole.deleteAll();
    }

    //@Test
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

    //@Test
    void userInsertion() {
        User user1 = new User("jdoe", "azerty", "jdoe@mygarage.com", "Doe", "John");
        daoUser.add(user1);
        Role adminRole = daoRole.selectOne(1);
        adminRole.getUsers().add(user1); //donne le role admin
        daoRole.update(adminRole);

        daoUser.update(user1);
        User user2 = new User("mdurand", "zqsd", "mdurand@mygarage.com", "Durand", "Marie");
        daoUser.add(user2);
        Role magRole = daoRole.selectOne(2);
        magRole.getUsers().add(user2); //donne le role magasinier
        daoRole.update(magRole);

        User chefMeca = new User("pmartin", "123", "pmartin@mygarage.com", "Martin", "Pierre");
        daoUser.add(chefMeca);
        Role chefRole = daoRole.selectOne(3);
        chefRole.getUsers().add(chefMeca); //donne le role chef_atelier
        Role mecafRole = daoRole.selectOne(4);
        chefRole.getUsers().add(chefMeca); //donne le role mecanicien
        daoRole.update(chefRole);
        daoRole.update(mecafRole);

        User commercial = new User("glopez", "abcd", "glopez@mygarage.com", "Lopez", "Gabriela");
        daoUser.add(commercial);
        Role comRole = daoRole.selectOne(5);
        comRole.getUsers().add(commercial); //donne le role mecanicien
        daoRole.update(comRole);

        User superUser = new User("emusk", "spacex", "emusk@mygarage.com", "Musk", "Elon");
        daoUser.add(superUser);
        for(int i=1; i<=5; i++){ //donne tous les roles
            Role r = daoRole.selectOne(i);
            r.getUsers().add(superUser);
            daoRole.update(r);
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

    @Test
    void ficheInsertion(){
        FicheEntretien f = new FicheEntretien();
        daoFiche.add(f);

        Tache t = new Tache("vidange", TypeTache.Vidange);
        daoTache.add(t);

        t.addPiece(daoPiece.selectOne(8)); //ajout piece Huile moteur 1L
        f.ajouterTache(t);

        f = new FicheEntretien();
        daoFiche.add(f);

        t = new Tache("Changer carbu", TypeTache.Pannes);
        daoTache.add(t);

        t.addPiece(daoPiece.selectOne(5)); //ajout piece Carburateur
        f.ajouterTache(t);
    }

    @Test
    void testCascade(){
        User u = new User("test", "azerty", "test@mygarage.com", "TEST", "Test");
        Role r = new Role("Test");
        daoRole.add(r);
        daoUser.add(u);

        u.addRole(r);
        daoUser.update(u);

/*
        Role r = new Role("TEST2");
        r.getUsers().add(u);
        daoRole.add(r);

 */
    }


}
