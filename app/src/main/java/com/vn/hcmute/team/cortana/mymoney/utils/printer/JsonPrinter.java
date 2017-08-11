package com.vn.hcmute.team.cortana.mymoney.utils.printer;

import com.google.gson.Gson;

/**
 * Created by infamouSs on 7/21/17.
 */

public class JsonPrinter implements Printer {
    
    public static JsonPrinter INSTANCE = new JsonPrinter();
    
    private Gson mGson;
    
    private JsonPrinter() {
        this.mGson = new Gson();
    }
    
    public static JsonPrinter getInstance() {
        return INSTANCE;
    }
    
    @Override
    public String print(Object obj) {
        return "[" + obj.getClass().getSimpleName() + ": " + mGson.toJson(obj) + "]";
    }
}
