package me.zjls.shoutinvite.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.zjls.shoutinvite.Main;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private Main plugin;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private boolean useSSL;

    private Connection connection;

    public MySQL(Main plugin) {
        this.plugin = plugin;
        host = plugin.getConfig().getString("mysql.host");
        port = plugin.getConfig().getString("mysql.port");
        database = plugin.getConfig().getString("mysql.database");
        username = plugin.getConfig().getString("mysql.username");
        password = plugin.getConfig().getString("mysql.password");
        useSSL = plugin.getConfig().getBoolean("mysql.useSSL");
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
            config.setUsername(username);
            config.setPassword(password);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            try (HikariDataSource ds = new HikariDataSource(config)) {
                connection = ds.getConnection();
            }
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
