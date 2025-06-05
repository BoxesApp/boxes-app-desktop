package com.boxesapp.desktopapp.model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRepo {
    public static void main(String[] argvs){
        System.out.println("TestRepo");
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        try (Connection connection = dbConnection.getConnection()) {
            // Use the connection for database operations
            // Example: create a statement and execute a query
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");

            while(rs.next() ){
                System.out.println(rs.getString("password"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
