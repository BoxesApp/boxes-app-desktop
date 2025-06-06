package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.Credential;
import com.boxesapp.desktopapp.model.dao.CredentialDao;
import com.boxesapp.desktopapp.utils.Log;
import com.boxesapp.desktopapp.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CredentialRepository implements CredentialDao {
    private static final String SQL_CREATE_CREDENTIAL = "insert into credentials (id_account, title, content) values (?, ?, ?)";
    private DatabaseConnetion dbConnection;

    public CredentialRepository(){
        dbConnection = DatabaseConnetion.getInstance();
    }

    @Override
    public void create(Credential credential) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_CREDENTIAL);

            preparedStatement.setInt(1, Integer.parseInt( credential.getAccount().getId().toString()));
            preparedStatement.setString(2, credential.getTitle());
            preparedStatement.setString(3, credential.getContent());

            preparedStatement.executeUpdate();
            credential.setId((long) preparedStatement.getResultSet().getInt("id"));
            Logger.log(new Log("a credential has been created", Log.LogLevel.low));

        } catch (SQLException e) {
            System.out.printf("SQL Exception: %s\n", e.getMessage());
            Logger.log(new Log( e.getMessage(), Log.LogLevel.exception));
        }
    }

    @Override
    public void update(Credential credential) {

    }

    @Override
    public void delete(Credential credential) {

    }

    @Override
    public Credential findById(Integer id) {
        return null;
    }

    @Override
    public List<Credential> findAll() {
        return List.of();
    }

    @Override
    public List<Credential> getByAccount(Account account){
        return List.of();
    }
}
