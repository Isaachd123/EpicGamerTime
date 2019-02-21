package server.models.services;

import server.Logger;
import server.DatabaseConnection;
import server.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public static String selectAllInto(List<User> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT UserId, Username, Name, Password, Token FROM Users"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new User(results.getInt("UserId"), results.getString("Username"), results.getString("Name"), results.getString("Password"), results.getString("Token")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Users' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static User selectById(int id) {
        User result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT UserId, Username, Name, Password, Token FROM Users WHERE UserId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new User(results.getInt("UserId"), results.getString("Username"), results.getString("Name"), results.getString("Password"), results.getString("Token"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Users' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(User itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Users (UserId, Username, Name, Password, Token) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, itemToSave.getUserId());
            statement.setString(2, itemToSave.getUsername());
            statement.setString(3, itemToSave.getName());
            statement.setString(4, itemToSave.getPassword());
            statement.setString(5, itemToSave.getToken());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Users' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(User itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Users SET Username = ?, Name = ?, Password = ?, Token = ? WHERE UserId = ?"
            );
            statement.setString(1, itemToSave.getUsername());
            statement.setString(2, itemToSave.getName());
            statement.setString(3, itemToSave.getPassword());
            statement.setString(4, itemToSave.getToken());
            statement.setInt(5, itemToSave.getUserId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Users' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Users WHERE UserId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Users' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String validateSessionCookie(javax.ws.rs.core.Cookie sessionCookie) {
        if (sessionCookie != null) {
            String token = sessionCookie.getValue();
            String result = UserService.selectAllInto(User.users);
            if (result.equals("OK")) {
                for (User u : User.users) {
                    if (u.getToken().equals(token)) {
                        Logger.log("Valid session token received.");
                        return u.getUsername();
                    }
                }
            }
        }
        Logger.log("Error: Invalid user session token");
        return null;
    }
}