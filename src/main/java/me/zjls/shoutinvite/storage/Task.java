package me.zjls.shoutinvite.storage;

import me.zjls.shoutinvite.Main;

import java.sql.SQLException;

public class Task implements Runnable {

    private Main plugin;

    public Task(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!plugin.getSQL().isConnected()) {
            try {
                plugin.getSQL().connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
