package com.ordina.session;

import com.ordina.entity.Addresses;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressesFacade extends AbstractFacade<Addresses> {
    @PersistenceContext(unitName = "com.ordina_Test3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressesFacade() {
        super(Addresses.class);
    }
    
}
