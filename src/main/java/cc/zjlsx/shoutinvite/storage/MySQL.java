package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.md_5.bungee.config.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private Main plugin;

    private Configuration config;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private boolean useSSL;

    private Connection connection;

    public MySQL(Main plugin) {
        this.plugin = plugin;
        config = plugin.getConfigManager().getConfig();
        host = config.getString("mysql.host");
        port = config.getString("mysql.port");
        database = config.getString("mysql.database");
        username = config.getString("mysql.username");
        password = config.getString("mysql.password");
        useSSL = config.getBoolean("mysql.useSSL");
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
