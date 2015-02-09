/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ordina.session;

import com.ordina.entity.Email;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tsl20897
 */
@Stateless
public class EmailFacade extends AbstractFacade<Email> {
    @PersistenceContext(unitName = "com.ordina_Test3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

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
  
    
}
