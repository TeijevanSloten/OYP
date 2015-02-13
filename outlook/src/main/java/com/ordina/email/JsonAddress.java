package com.ordina.email;

import com.ordina.entity.Addresses;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import org.json.JSONObject;

public class JsonAddress {
    
    public JSONObject wrapJsonAddresses(List<Addresses> addresses) {
        
       // JsonObject Jobj = Json.createObjectBuilder();
        JSONObject obj = new JSONObject();
        
        for(Addresses address : addresses) {
            obj.put("fullname", address.getName());
            obj.put("email", address.getEmail());
        }        
        return obj;
    }
    
}
