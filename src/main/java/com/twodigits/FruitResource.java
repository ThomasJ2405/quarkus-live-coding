package com.twodigits;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("fruits")
public class FruitResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruit> get() {
        return Fruit.listAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Fruit fruit) {
        fruit.id = null;
        fruit.persist();
        return Response.status(Response.Status.CREATED).entity(fruit).build();
    }
}
