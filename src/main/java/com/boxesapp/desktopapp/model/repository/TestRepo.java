package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class TestRepo {
    public static void main(String[] argvs){

//        tesListUsers();
//        System.out.println("TestRepo");
////        testCreateUser();
//        testDeleteUser();
//        tesListUsers();
//        testFindById(1);
//        testUpdateUser();
        testFindByEmailAndPassword("test@gmail.com", "aaaa");
    }


    public static void tesListUsers(){
        DatabaseConnetion dbConnection = DatabaseConnetion.getInstance();

        try (Connection connection = dbConnection.getConnection()) {
            // Use the connection for database operations
            // Example: create a statement and execute a query
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");

            while(rs.next() ){
                System.out.println(rs.getString("username"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testCreateUser(){
        User item = new User(
                0L,
                "user2",
                "emil@gwe.ewe",
                "Password",
                0L,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        new UserRepository().create(item);
    }

    public static void testDeleteUser(){
        User item = new User(
                2L,
                "user3",
                "emil@gwe.ewe",
                "Password",
                0L,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        new UserRepository().delete(item);
    }

    public static void testFindById(int id){
        User foundeduser = new UserRepository().findById((long) id);
        System.out.println("user founded: " + foundeduser);
    }

    public static void testUpdateUser(){
        User item = new UserRepository().findById((long) 1);
        System.out.println("user befor update: " + item);
        item.setRemoteId(item.getRemoteId()+1);
        new UserRepository().update(item);
        System.out.println("user after update: " + item);
    }

    public static void testFindByEmailAndPassword(String email, String password){
        User item = new UserRepository().findByEmailAndPassword(email, password);
        System.out.println("user found: " + item);
    }
}
