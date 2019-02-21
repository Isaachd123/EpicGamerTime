package server.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user/")
public class UserController {
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@FormParam(), @FormParam()) {
        return;
    }
}
//unfinished