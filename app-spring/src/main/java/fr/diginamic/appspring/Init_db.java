package fr.diginamic.appspring;

import javax.annotation.PostConstruct;

import fr.diginamic.appspring.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.diginamic.appspring.dao.DaoRole;
import fr.diginamic.appspring.entities.Role;
import fr.diginamic.appspring.entities.User;
import fr.diginamic.appspring.enums.ApplicationUserRole;
//import fr.diginamic.appspring.repository.CrudRoleRepo;

@Component
public class Init_db {

    @Autowired
    private CrudUserRepository ur;

    @Autowired
//    private CrudRoleRepo rr;
    private DaoRole daoRole;

    @Autowired
    private PasswordEncoder pwdEncoder;


    @PostConstruct 
    public void init() {

        Role admin = new Role(ApplicationUserRole.ADMIN.name());
//        rr.save(admin);
        daoRole.add(admin);

        Role magasinier = new Role(ApplicationUserRole.MAGASINIER.name());
//        rr.save(admin);
        daoRole.add(magasinier);

        Role commercial = new Role(ApplicationUserRole.COMMERCIAL.name());
//        rr.save(admin);
        daoRole.add(commercial);

        Role mecanicien = new Role(ApplicationUserRole.MECANICIEN.name());
//        rr.save(admin);
        daoRole.add(mecanicien);

        Role chef = new Role(ApplicationUserRole.CHEF.name());
//        rr.save(chef);
        daoRole.add(chef);

        User u1 = new User("a", pwdEncoder.encode("123"), "email", "nom", "albert");
        ur.save(u1);

        User u2 = new User("c", pwdEncoder.encode("123"), "email", "nom",  "claire");
        ur.save(u2);

        u1.getUserRoles().add(admin);
        u1.getUserRoles().add(chef);
        admin.getUsers().add(u1);
        chef.getUsers().add(u1);

        u2.getUserRoles().add(chef);
        chef.getUsers().add(u2);

//        u1.addRole(admin);
//        u1.addRole(chef);
//        admin.addUser(u1);
//        chef.addUser(u1);
//
//        u2.addRole(chef);
//        chef.addUser(u2);

        ur.save(u1);
        ur.save(u2);
//        rr.save(admin);
//        rr.save(chef);
        daoRole.update(admin);
        daoRole.update(chef);

    }

}
