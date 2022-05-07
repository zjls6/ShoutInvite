package me.zjls.shoutinvite;

import lombok.Getter;
import lombok.Setter;
import me.zjls.shoutinvite.commands.InviteTeleport;
import me.zjls.shoutinvite.commands.Shout;
import me.zjls.shoutinvite.listeners.PlayerJoin;
import me.zjls.shoutinvite.storage.MySQL;
import me.zjls.shoutinvite.storage.SQLGetter;
import me.zjls.shoutinvite.storage.Task;
import me.zjls.shoutinvite.utils.Color;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Main extends Plugin {

    public File configFile;
    public Configuration config;
    public MySQL SQL;
    public SQLGetter data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdir()) {

            }
        }

        configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SQL = new MySQL(this);
        data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (SQLException e) {
            getLogger().warning(Color.s("&c&l数据库连接失败"));
            e.printStackTrace();
        }

        if (SQL.isConnected()) {
            getLogger().info(Color.s("&a数据库连接成功"));
            data.createTable();
        } else {
            getLogger().warning("&c&l数据库连接失败");
        }

        getProxy().getScheduler().schedule(this, new Task(this), 20, 20, TimeUnit.SECONDS);
        getProxy().getPluginManager().registerCommand(this, new InviteTeleport(this));
//        getProxy().getPluginManager().registerCommand(this, new ShoutInvite(this));
        getProxy().getPluginManager().registerCommand(this, new Shout(this));
        getProxy().getPluginManager().registerListener(this, new PlayerJoin());

        getLogger().info("§a插件已开启");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SQL.disConnect();
    }
}
