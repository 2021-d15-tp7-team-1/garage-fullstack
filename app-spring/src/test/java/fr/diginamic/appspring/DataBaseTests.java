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
    Init_db initDB;

    @Test
    void remplirDatabase(){
        initDB.init();
    }


}
