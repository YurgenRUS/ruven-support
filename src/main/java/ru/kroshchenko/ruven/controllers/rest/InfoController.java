package ru.kroshchenko.ruven.controllers.rest;

import ru.kroshchenko.ruven.controllers.rest.resources.ApplicationInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Kroshchenko
 *         2016.05.05
 */
@Path("/app")
public class InfoController {

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationInfo getInfo() {
        return new ApplicationInfo("unknown");
    }
}
