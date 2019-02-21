package server.controllers;

import server.Logger;
import server.models.User;
import server.models.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("user/")
public class UserController {
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@FormParam("username") String username,
                        @FormParam("password") String password) {
        Logger.log("User " + username + " is attempting to login");

        UserService.selectAllInto(User.users);

        for (User u: User.users) {
            if (u.getUsername().toLowerCase().equals(username.toLowerCase())) {
                if (!u.getPassword().equals(password)) {
                    return "Error: Incorrect password";
                }

                String token = UUID.randomUUID().toString();
                u.setToken(token);
                String success = UserService.update(u);

                if (success.equals("OK")) {
                    return token;
                } else {
                    return "Error: Can't create session token.";
                }
            }
        }
        return "Error: Can't find user account.";
    }
}
