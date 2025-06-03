package com.boxesapp.desktopapp.utils;

import java.sql.Timestamp;

/**
 * Class for crafting a Log
 */
public class Log {
    private String message;
    private LogLevel level;

    private Timestamp timestamp;

    public enum LogLevel {
        /**
         * / any exception occurred in the app like failed logging and real exception.
         */
        exception,
        /**
         * Any behaviour in the app like login, and "CRUDing" accounts & credentials
         */
        low,
        /**
         * Log for any major stuffs like backup, editing passwords
         */
        medium
    }

    private Log(String message){
        this.message = message;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.level = LogLevel.low;
    }

    public Log(String message, LogLevel logLevel) {
        new Log(message);
        this.level = logLevel;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return " -- Log level: " + level + " -- timestamp " + timestamp.toString() + " -- message " + message + "\n";
    }
}
