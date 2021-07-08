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

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("fruits")
public class FruitResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(
        name = "fruits.get.counter", 
        description = "How many calls for get have been registered.")
    @Timed(
        name = "fruits.get.timer",
        description = "How long did the invocations of get take time.",
        unit = MetricUnits.MILLISECONDS)
    public List<Fruit> get() {
        return Fruit.listAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(
        name = "fruit.create.counter", 
        description = "How many fruits  have been created.")
    @Timed(
        name = "fruit.create.timer",
        description = "How long did the invocations of create take time.",
        unit = MetricUnits.MILLISECONDS)
    public Response create(Fruit fruit) {
        fruit.id = null;
        fruit.persist();
        return Response.status(Response.Status.CREATED).entity(fruit).build();
    }
}
