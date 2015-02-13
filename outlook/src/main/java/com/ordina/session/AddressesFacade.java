package com.ordina.session;

import com.ordina.entity.Addresses;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressesFacade extends AbstractFacade<Addresses> {
    @PersistenceContext(unitName = "com.ordina_Test3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private static final Comparator<Addresses> ORDER_BY_NAME = 
                                        new Comparator<Addresses>() {
            public int compare(Addresses e1, Addresses e2) {
                return e1.getName().compareTo(e2.getName());
            }
    };
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressesFacade() {
        super(Addresses.class);
    }
    
     public List<Addresses> findByName() {
        List<Addresses> addresses = getEntityManager().createNamedQuery("Addresses.findAll", Addresses .class).getResultList();
        Collections.sort(addresses, ORDER_BY_NAME); 
        return addresses;
    }
}
