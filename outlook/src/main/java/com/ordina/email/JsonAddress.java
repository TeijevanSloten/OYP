/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ordina.email;

import com.ordina.entity.Addresses;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import org.json.JSONObject;

/**
 *
 * @author tni20865
 */
public class JsonAddress {
    
    public JSONObject wrapJsonAddresses(List<Addresses> addresses) {
        
       // JsonObject Jobj = Json.createObjectBuilder();
        JSONObject obj = new JSONObject();
        
        for(Addresses address : addresses) {
            obj.put("fullname", address.getName());
            obj.put("email", address.getEmail());
        }
        
        /*
        String json = "{\"addresses\":[\n";
        for (Addresses address : addresses) {
            json += "\t{\"fullname\":\"" + address.getName() + "\", \"email\":\"" + address.getEmail() + "\"},\n";
        }
        json = json.substring(0, json.length() - 2) + "\n]";
        */ 
                
        return obj;
    }
    
}
