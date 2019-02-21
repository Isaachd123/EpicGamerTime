package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class User {
    private int userId;
    private String username;
    private String name;
    private String password;
    private String token;

    public User(int userId, String username, String name, String password, String token) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static ArrayList<User> users = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (User u: users) {
            if (u.getUserId() > id) {
                id = u.getUserId();
            }
        }
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("userId", getUserId());
        j.put("username", getUsername());
        j.put("name", getName());
        j.put("password", getPassword());
        j.put("token", getToken());

        return j;
    }
}