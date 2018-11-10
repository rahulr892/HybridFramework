package com.rahul.tests.connectDB;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.rahul.inputs.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectDBWithSSH {

    private final static String sshHost = "sshHostName";
    private final static String sshUsername = "sshUsername";
    private final static int sshPort = 22;
    private final static String SshKeyFilePath = Constants.SSH_KEY;

    private final static int localPort = 8740; // any free port can be used
    private final static String remoteHost ="remoteHostString";
    private final static int remotePort = 3306;

    private final static String dbuserName = "dbuserName";
    private final static String dbpassword = "dbpassword";
    private final static String localSSHUrl = "localSSHUrl";

    private final static String databaseName = "sampleDBName";

    private static Connection connection = null;
    private static Session session = null;

    public static void connectToServer(String dataBaseName) {
        connectSSH();
        connectToDataBase(dataBaseName);
    }


    private static void connectSSH() {
        try {
            java.util.Properties config = new java.util.Properties();
            JSch jsch = new JSch();
            session = jsch.getSession(sshUsername, sshHost, sshPort);
            jsch.addIdentity(SshKeyFilePath);
            config.put("StrictHostKeyChecking", "no");
            config.put("ConnectionAttempts", "1");
            session.setConfig(config);
            session.connect();

            System.out.println("SSH Connected");

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            int assigned_port = session.setPortForwardingL(localPort, remoteHost, remotePort);

            System.out.println("localhost:" + assigned_port + " -> " + remoteHost + ":" + remotePort);
            System.out.println("Port Forwarded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void connectToDataBase(String dataBaseName) {
        try {
            //mysql database connectivity
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(localSSHUrl);
            dataSource.setPortNumber(localPort);
            dataSource.setUser(dbuserName);
            //dataSource.setAllowMultiQueries(true);

            dataSource.setPassword(dbpassword);
            dataSource.setDatabaseName(dataBaseName);

            connection = dataSource.getConnection();

            System.out.print("Connection to server successful!:" + connection + "\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void CloseDataBaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Closing Database Connection");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void CloseSSHConnection() {
        if (session != null && session.isConnected()) {
            System.out.println("Closing SSH Connection");
            session.disconnect();
        }
    }


    public static void closeConnections() {
        CloseDataBaseConnection();
        CloseSSHConnection();
    }


    public static void main(String[] args) {
        String query = "SELECT * FROM tableName ORDER BY SUBMITTED_AT DESC LIMIT 1";

        ResultSet resultSet = null;
        try {
            connectToServer(databaseName);
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);
            System.out.println("Database connection success " + resultSet);
            while (resultSet.next()) {
                System.out.println("StatusID : " + resultSet.getString("STATUSID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnections();
    }

}
