package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.Credential;
import com.boxesapp.desktopapp.model.dao.CredentialDao;
import com.boxesapp.desktopapp.utils.Log;
import com.boxesapp.desktopapp.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CredentialRepository implements CredentialDao {
    private static final String SQL_CREATE = "insert into credentials (id_account, title, content) values (?, ?, ?)";
    private static final String SQL_UPDATE = "update credentials set title = ?, content = ? where id = ?";
    private static final String SQL_DELETE = "delete from credentials where id = ?";
    private static final String SQL_DELETE_BY_ACCOUNT = "delete from credentials where id_account = ?";
    private static final String SQL_FIND_BY_ID = "select * from credentials where id = ?";
    private static final String SQL_FIND_BY_ID_ACCOUNT = "select * from credentials where id_account = ?";
    private DatabaseConnetion dbConnection;

    public CredentialRepository(){
        dbConnection = DatabaseConnetion.getInstance();
    }

    @Override
    public void create(Credential credential) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);

            preparedStatement.setInt(1, Integer.parseInt( credential.getAccount().getId().toString()));
            preparedStatement.setString(2, credential.getTitle());
            preparedStatement.setString(3, credential.getContent());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                credential.setId(resultSet.getInt(1));
            }
            Logger.log(new Log("a credential has been created", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
    }

    @Override
    public void update(Credential credential) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, credential.getTitle());
            preparedStatement.setString(2, credential.getContent());
            preparedStatement.setInt(3, credential.getId());

            preparedStatement.executeUpdate();

            Logger.log(new Log("a credential has been updated", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        credential = findById(credential.getId());
    }

    @Override
    public void delete(Credential credential) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(1, credential.getId());

            preparedStatement.executeUpdate();
            Logger.log(new Log("a credential has been deleted", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        credential = null;
    }

    /**
     *
     * Delete all credentials related to an account
     * @param account
     */
    public void delete(Account account){
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ACCOUNT);

            preparedStatement.setInt(1, account.getId());

            preparedStatement.executeUpdate();
            Logger.log(new Log(   " credential(s) has(have) been deleted by account", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

        // refresh des entites accounts
    }

    @Override
    public Credential findById(Integer id) {
        Credential credential = null;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            credential = new Credential();
            if (rs.next()) {
                credential.setId(id);
                credential.setTitle(rs.getString("title"));
                credential.setContent(rs.getString("content"));
                credential.setAccount(new AccountRepository().findById(rs.getInt("id_account")));
                credential.setCreatedAt(rs.getTimestamp("created_at"));
                credential.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

            Logger.log(new Log(   "a  credential has been found by account", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }

        return credential;
    }

    @Override
    public List<Credential> findAll() {
        return List.of();
    }


    /**
     * get credentials by account
     * @param account
     * @return
     */
    @Override
    public List<Credential> getByAccount(Account account){
        Credential credential;
        ArrayList<Credential> result = null;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID_ACCOUNT);

            preparedStatement.setInt(1, account.getId());

            ResultSet rs = preparedStatement.executeQuery();

            result = new ArrayList<>();
            while (rs.next()) {
                credential = new Credential();
                credential.setId(rs.getInt("id"));
                credential.setTitle(rs.getString("title"));
                credential.setContent(rs.getString("content"));
                credential.setAccount(new AccountRepository().findById(rs.getInt("id_account")));
                credential.setCreatedAt(rs.getTimestamp("created_at"));
                credential.setUpdatedAt(rs.getTimestamp("updated_at"));

                result.add(credential);
            }

            Logger.log(new Log(   "credential(s) have been found by account", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
        return result;
    }
}
