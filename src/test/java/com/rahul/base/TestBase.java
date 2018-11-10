package com.rahul.base;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.rahul.inputs.Constants;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class TestBase {

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
    protected Statement statement;


    //@BeforeSuite
    public void connectToServer() {
        connectSSH();
        connectToDataBase(databaseName);
    }


    private void connectSSH() {
        try {
            Properties config = new Properties();
            JSch jsch = new JSch();
            session = jsch.getSession(sshUsername, sshHost, sshPort);
            jsch.addIdentity(SshKeyFilePath);
            config.put("StrictHostKeyChecking", "no");
            config.put("ConnectionAttempts", "2");
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


    private void connectToDataBase(String dataBaseName) {
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


    private void CloseDataBaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Closing Database Connection");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void CloseSSHConnection() {
        if (session != null && session.isConnected()) {
            System.out.println("Closing SSH Connection");
            session.disconnect();
        }
    }


    //@AfterSuite
    public void closeConnections() {
        CloseDataBaseConnection();
        CloseSSHConnection();
    }


    public ResultSet getData(String query) throws Exception {
        if (connection == null) {
            System.out.println("Connecting DB Again in getData");
            connectToServer();
        }
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }


    protected String getStringDataFromColumn(String columnName, String query) throws Exception {
        if (connection == null) {
            System.out.println("Connecting DB Again in getStringDataFromColumn");
            connectToServer();
        }
        Statement stmt = connection.createStatement();
        String resultValue = null;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            resultValue = rs.getString(columnName);
        }
        return resultValue;
    }


    protected String getStringData(String query) throws Exception {
        if (connection == null) {
            System.out.println("Connecting DB Again in getStringData");
            connectToServer();
        }
        Statement stmt = connection.createStatement();
        String resultValue = null;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            resultValue = rs.getString(1);
        }
        return resultValue;
    }


    // Check to assert value is present in DB table w.r.t query
    protected void checkValueIsPresentInDBTable(String value, String sqlQuery, String assertMessage) throws Exception {
        boolean flag = false;
        ArrayList<String> listOfDBValues;
        // Getting list of values from query
        listOfDBValues = getArrayListStringData(sqlQuery);
        for (String strName : listOfDBValues) {
            if (strName.equalsIgnoreCase(value)) {
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, assertMessage);
    }


    private ArrayList<String> getArrayListStringData(String query) throws Exception {
        if (connection == null) {
            System.out.println("Connecting DB Again in getArrayListStringData");
            connectToServer();
        }
        Statement stmt = connection.createStatement();
        ArrayList<String> resultValue = new ArrayList<String>();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int columnCount = rs.getMetaData().getColumnCount();
            StringBuilder stringBuilder = new StringBuilder();
            for (int iCounter = 1; iCounter <= columnCount; iCounter++) {
                stringBuilder.append(rs.getString(iCounter).trim() + " ");
            }
            String reqValue = stringBuilder.substring(0, stringBuilder.length() - 1);
            resultValue.add(reqValue);
        }
        return resultValue;
    }


    protected void executeUpdate(String query) throws Exception {
        if (connection == null) {
            System.out.println("Connecting DB Again in executeUpdate");
            connectToServer();
        }
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }


    protected void assertDBData(String columnName, String query, String expectedValue) throws Exception {
        Assert.assertTrue(getStringDataFromColumn(columnName, query).equals(expectedValue));
    }


    protected String readFromPropertiesFile(String key) {
        String value = "";
        try {
            Properties properties = new Properties();
            File file = new File(Constants.ENV_PROPERTIES);
            if (file.exists()) {
                properties.load(new FileInputStream(file));
                value = properties.getProperty(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    protected void addToPropertiesFile(String key, String val) {
        try {
            File file = new File(Constants.ENV_PROPERTIES);
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            FileOutputStream obj = new FileOutputStream(file);
            properties.setProperty(key, val);
            properties.store(obj, "Update data into file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

