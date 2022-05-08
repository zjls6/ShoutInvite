package me.zjls.shoutinvite.storage;

import lombok.Data;
import me.zjls.shoutinvite.Main;
import me.zjls.shoutinvite.enums.Messages;
import me.zjls.shoutinvite.utils.Color;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Data
public class ConfigManager {

    public File configFile;
    public Configuration config;
    public int coolDownTime;
    public int expiryTime;

    public List<String> blockedServers;

    private Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;

        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                plugin.getLogger().severe(Color.s("&c创建配置文件夹失败"));
            }
        }

        configFile = new File(dataFolder, "config.yml");

        //检测配置文件是否存在
        if (!configFile.exists()) {
            //不存在，则复制配置文件
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载配置文件
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(dataFolder, "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadMessages();

        coolDownTime = config.getInt("times.cooldown");
        expiryTime = config.getInt("times.expiry");

        blockedServers = config.getStringList("blockedServers");
    }

    public void reloadMessages() {
        //加载消息
        for (Messages message : Messages.values()) {
            message.setMessage(config.getString(message.getConfigPath()));
        }
    }


}
