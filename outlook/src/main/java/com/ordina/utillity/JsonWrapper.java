package com.ordina.utillity;

import com.ordina.entity.Addresses;
import java.util.List;

public class JsonWrapper {
    
    public static String wrapJsonAddresses(List<Addresses> addresses) {
         String json = "{\"addresses\":[\n";
        for (Addresses address : addresses) {
            json += "\t{\"fullname\":\"" + address.getName() + "\", \"email\":\"" + address.getEmail() + "\"},\n";
        }
        json = json.substring(0, json.length() - 2) + "\n]";
        return json;
    }
}
