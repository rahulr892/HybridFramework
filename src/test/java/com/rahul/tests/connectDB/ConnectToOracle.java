package com.rahul.tests.connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class ConnectToOracle {

    private Connection connection = null;
    private Statement statement;

    private void startDBConn() throws Exception {
        String hostname = "hostName";
        String dsn = "dsn";
        String dbuser = "dbuser";
        String dbpassword = "dbpassword";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":1521:" + dsn, dbuser, dbpassword);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeDBConn() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

}
