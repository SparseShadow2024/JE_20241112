package com.sparseshadow.je_20241112.handler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")  
public class IndexHandler {

    @GET
    public String index() {
        return "Hello World!";
    }
}
