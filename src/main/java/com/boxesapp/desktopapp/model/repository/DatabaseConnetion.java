package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.utils.CustomRessources;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * singleton for configuring access datasbases
 */
public class DatabaseConnetion {

    private static volatile DatabaseConnetion instance; // volative is for thread safe
    private Connection connection;
    private static final String dbName = "boxes_app_desktop.sqlite";

    private static final String DB_URL = "jdbc:sqlite:"+ (CustomRessources.dbDir == null ? "" : CustomRessources.dbDir) + dbName;

    // initialize a connexion ?
    private void DBConnection(){
        try{
            try{
                new File(CustomRessources.dbDir).mkdirs();
            }catch (Exception e){

            }
            this.connection = DriverManager.getConnection(DB_URL);
        }catch (Exception e){
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    // create an instance of the database
    public static DatabaseConnetion getInstance(){
        // checking instance with and without instance to make sure of the state of instances across threads
        if(instance == null){
            synchronized (DatabaseConnetion.class){ // s
                if(instance == null){
                    instance = new DatabaseConnetion();
                }
            }
        }
        return instance;
    }

    // get the Connexion object generated. if its null, we create it
    public Connection getConnection(){
        try{
            // checking connection with and without instance to make sure of the state of connections across threads
            if(this.connection == null || this.connection.isClosed()){
                synchronized (DatabaseConnetion.class){
                    if(this.connection == null || this.connection.isClosed()){
                        DBConnection();
                    }
                }

            }
        }catch (SQLException e){
            throw new RuntimeException("Failed to initialize database connection", e);
        }
        return connection;
    }

    // close the connexion if it exists | thread safe
    public void closeConnection(){
        try{
            if(this.connection != null && !this.connection.isClosed()){
                synchronized (DatabaseConnetion.class){
                    if(this.connection != null && !this.connection.isClosed()){
                        this.connection.close();
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Failed to close database connection", e);
        }
    }
}