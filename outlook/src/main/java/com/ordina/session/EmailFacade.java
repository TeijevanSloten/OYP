package com.ordina.session;

import com.ordina.entity.Email;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmailFacade extends AbstractFacade<Email> {
    @PersistenceContext(unitName = "com.ordina_Test3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private static final Comparator<Email> ORDER_BY_DATE = 
                                        new Comparator<Email>() {
            public int compare(Email e1, Email e2) {
                return e2.getDate().compareTo(e1.getDate());
            }
    };
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmailFacade() {
        super(Email.class);
    }
    
    public List<Email> findMessageId(int id) {
        return getEntityManager().createNamedQuery("Email.findByMessageid", Email .class).setParameter("messageid", id).getResultList();
    }
    
    public List<Email> findAllByDate() {
        List<Email> emails = getEntityManager().createNamedQuery("Email.findAll", Email .class).getResultList();
         Collections.sort(emails, ORDER_BY_DATE);
        return emails;
    }
}
