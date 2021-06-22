package me.zjls.shoutinvite.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private String host = "kllczjls.mysql.rds.aliyuncs.com";
    private String port = "3306";
    private String useSSL = "true";
    private String database = "test";
    private String username = "root";
    private String password = "Kllc1723&Zjls";

    private Connection connection;

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + useSSL,
                    username, password);
        }
    }

    public void disConnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
