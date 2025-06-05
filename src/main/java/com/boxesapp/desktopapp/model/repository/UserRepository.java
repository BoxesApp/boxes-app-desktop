package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.User;
import com.boxesapp.desktopapp.model.dao.UserDao;
import com.boxesapp.desktopapp.utils.Log;
import com.boxesapp.desktopapp.utils.Logger;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class UserRepository implements UserDao {

    private static String CREATE_USER_SQL = "INSERT INTO users (username, email ,password, remote_id) VALUES (?, ?, ?, ?)";
    private static String FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
    private static  String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static String UPDATE_USER_SQL = "UPDATE users SET username = ?, email = ?, password = ?, remote_id = ? WHERE id = ?";





    @Override
    public void create(User user) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_SQL);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, Integer.parseInt(user.getRemoteId().toString()));

            preparedStatement.executeUpdate();
            user.setId((long) preparedStatement.getResultSet().getInt("id"));
            Logger.log(new Log("a user has been created", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

    }

    @Override
    public void update(User user) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, Integer.parseInt(user.getRemoteId().toString()));
            preparedStatement.setInt(5, Integer.parseInt(user.getId().toString()));

            preparedStatement.executeUpdate();

            Logger.log(new Log("a user has been updated", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        user = findById(user.getId());
    }

    @Override
    public void delete(User user) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);

            preparedStatement.setInt(1, Integer.parseInt(user.getId().toString()));

            preparedStatement.executeUpdate();
            Logger.log(new Log("a user has been deleted", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
    }

    @Override
    public User findById(Long id) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        User user = null;

        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);

            preparedStatement.setInt(1, Integer.parseInt(id.toString()) );
            ResultSet rs = preparedStatement.executeQuery();


            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRemoteId( (long) rs.getInt("remote_id"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
                Logger.log(new Log("a user has been search by id "+id, Log.LogLevel.low));
            }



        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

        return user;
    }


    @Override
    public User findByEmailAndPassword(String email, String password) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        User user = null;

        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();


            if (rs.next()) {
                user = new User();
                user.setId( Long.parseLong(rs.getString("id")) );
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRemoteId( (long) rs.getInt("remote_id"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
                Logger.log(new Log("a user has been searched and found ", Log.LogLevel.low));
            }



        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    private ResultSet makeResultQuery(String queryString, Objects[] para) {
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();
        ResultSet result = null;

        try (Connection connection = dbConnection.getConnection()) {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(queryString);

        } catch (SQLException e) {
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
            result = null;
        }

        return result;
    }
}
