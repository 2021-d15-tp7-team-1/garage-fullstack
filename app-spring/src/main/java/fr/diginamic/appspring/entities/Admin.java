package fr.diginamic.appspring.entities;

import javax.persistence.Entity;

@Entity
public class Admin extends Role {
    public Admin() {
    }

    public Admin(String nomRole) {
        super(nomRole);
    }
}
