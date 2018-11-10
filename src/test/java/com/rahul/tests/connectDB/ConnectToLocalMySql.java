package com.rahul.tests.connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class ConnectToLocalMySql {

    private Connection connection = null;
    private Statement statement;
    private String hostname = "pcHostName";

    public void connectToServer() {
        // Change hostname value in Constants file
        String dbUrl = "jdbc:mysql://" + hostname + ":3306/localmysql_new?useSSL=false";
        String username = "username";
        String password = "password";
        connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Starting DB Connection...");
        } catch (Exception e) {
            System.out.println("Starting DB Connection Failed..." + e);
        }
    }

    public void closeConnections() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Closing DB Connection...");
            }
        } catch (Exception e) {
            System.out.println("Closing DB Connection Failed..." + e);
        }
    }

}
