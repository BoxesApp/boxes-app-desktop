package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.User;
import com.boxesapp.desktopapp.model.dao.AccountDao;
import com.boxesapp.desktopapp.model.dao.UserDao;
import com.boxesapp.desktopapp.utils.Log;
import com.boxesapp.desktopapp.utils.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements AccountDao {
    private static final String SQL_CREATE = "insert into accounts (name, description, id_owner) values (?, ?, ?)";
    private static final String SQL_UPDATE = "update accounts set name = ?, description = ?, id_owner = ? where id = ?";
    private static final String FIND_BY_ID = "select * from accounts inner join users on id_owner = users.id where accounts.id = ?";
    private static final String FIND_BY_OWNER = "select * from accounts where id_owner = ?";
    private static final String DELETE_BY_ID = "delete from accounts where id = ?";

    private DatabaseConnetion dbConnection;

    public AccountRepository() {
        dbConnection = new DatabaseConnetion();
    }

    @Override
    public List<Account> findByCredentialTitle() {
        return List.of();
    }

    @Override
    public void create(Account account) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);

            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getDescription());
            preparedStatement.setInt(3, Integer.parseInt(account.getOwner().getId().toString()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setId(generatedKeys.getInt(1));
            }

            Logger.log(new Log("an account has been created", Log.LogLevel.low));
        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
    }

    @Override
    public void update(Account account) {

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getDescription());
            preparedStatement.setInt(3, account.getOwner().getId());
            preparedStatement.setInt(4, account.getId());

            preparedStatement.executeUpdate();

            Logger.log(new Log("an account has been updated", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        account = findById(account.getId());
    }

    @Override
    public void delete(Account account) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);

            preparedStatement.setInt(1, account.getId());

            preparedStatement.executeUpdate();
            Logger.log(new Log("an account has been deleted", Log.LogLevel.low));
            account = null;
        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

    }

    @Override
    public Account findById(Integer id) {

        Account result = null;

        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setInt(1, Integer.parseInt(id.toString()) );
            ResultSet rs = preparedStatement.executeQuery();


            if (rs.next()) {
                result = new Account();
                result.setId(id);
                result.setName(rs.getString("name"));
                result.setDescription(rs.getString("email"));
                result.setOwner(
                        new UserRepository().findById(rs.getInt("id_owner"))
                );
                result.setCreatedAt(rs.getTimestamp("created_at"));
                result.setUpdatedAt(rs.getTimestamp("updated_at"));
                Logger.log(new Log("an account has been search by id "+id, Log.LogLevel.low));
            }
        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        return result;
    }

    @Override
    public List<Account> findByOwner(User owner){

        ArrayList<Account> result = null;

        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_OWNER);

            preparedStatement.setInt(1, owner.getId());
            ResultSet rs = preparedStatement.executeQuery();


            result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Account(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        owner,
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
            Logger.log(new Log( result.size()+ "accounts has been searched and found by owner ", Log.LogLevel.low));
        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        return result;
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }
}
