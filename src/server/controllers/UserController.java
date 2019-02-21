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

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String newUser(@FormParam("username") String username,
                          @FormParam("name") String name,
                          @FormParam("password") String password,
                          @FormParam("confirm password") String passwordConfirm) {
        Logger.log("User " + username + " is attempting to sign up");

        UserService.selectAllInto(User.users);

        for (User u: User.users) {
            if (u.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return "Error: Username already exists";
            }
        }
        if (!password.equals(passwordConfirm)) {
            return "Error: Passwords do not match";
        }

        int newid = User.nextId();

        String token = UUID.randomUUID().toString();

        String Status = UserService.insert(new User(newid, username, name, password, token));
        if (Status.equals("OK")) {
            Logger.log("Created user " + username);
            return token;
        } else {
            return "Error: cannot create new user";
        }
}
