package me.zjls.shoutinvite;

import me.zjls.shoutinvite.Commands.STP;
import me.zjls.shoutinvite.Commands.Shout;
import me.zjls.shoutinvite.Commands.ShoutInvite;
import me.zjls.shoutinvite.Listeners.PlayerJoin;
import me.zjls.shoutinvite.SQL.MySQL;
import me.zjls.shoutinvite.SQL.SQLGetter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Plugin {

    public static Main instance;
    public static File configFile;
    public static Configuration config;
    public static MySQL SQL;
    public static SQLGetter data;

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        SQL = new MySQL();
        data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (SQLException e) {
            getLogger().warning("§c§l数据库连接失败");
            e.printStackTrace();
        }

        if (SQL.isConnected()) {
            getLogger().info("§a数据库连接成功");
            data.createTable();
        } else {
            getLogger().warning("§c§l数据库连接失败");
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        configFile = new File(getDataFolder(), "config.yml");
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
                config.set("message.shout-format", "&6[喊话]&e[%server%(%players%)]&b[%player%]&8:&f%message%");
                config.set("message.invite-format", "&e[点击传送]");
                config.set("message.run-in-console", "§c该命令不能在控制台执行！");
                config.set("message.run-in-login", "§c该命令不能在登录服执行！");
                config.set("cooldown.time", 30);
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        getProxy().getPluginManager().registerCommand(this, new STP());
        getProxy().getPluginManager().registerCommand(this, new ShoutInvite());
        getProxy().getPluginManager().registerCommand(this, new Shout());
        getProxy().getPluginManager().registerListener(this, new PlayerJoin());

        getLogger().info("§a插件已开启");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SQL.disConnect();
    }
}
