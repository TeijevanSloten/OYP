/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ordina.session;

import com.ordina.entity.Profilesettings;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tsl20897
 */
@Stateless
public class ProfilesettingsFacade extends AbstractFacade<Profilesettings> {
    @PersistenceContext(unitName = "com.ordina_Test3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfilesettingsFacade() {
        super(Profilesettings.class);
    }
    
}
