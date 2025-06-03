package com.boxesapp.desktopapp.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * class to log anything in a file
 */
public class Logger {

    private String filePath;
    private File logFile;

    private static Logger instance = null;

    private Logger(String filePath) throws NullPointerException {
        if(Files.exists(logFile.toPath())){
            logFile = new File(filePath);
        }
    }


    // initialize the logger
    public static void init(String filePath) throws Exception {
        if (instance == null) {
            instance = new Logger(filePath);
        }

        throw new Exception("Logger is already up !");
    }

    // log action
    public static void log(Log log){

        try{
            if(instance == null){
                throw  new Exception("Logger not initialized. you should call Logger.init(filePath) first");
            }

            // creating and updating the file it doesn't exist
            instance.logFile.createNewFile();

            Files.write(instance.logFile.toPath(), log.toString().getBytes(), StandardOpenOption.APPEND);
        }catch (Exception ignored){

        }
    }



}
