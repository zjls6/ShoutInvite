package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.enums.Messages;
import cc.zjlsx.shoutinvite.utils.Color;
import lombok.Data;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ConfigManager {

    public File configFile;
    public Configuration config;
    public int coolDownTime;
    public int expiryTime;

    public List<String> blockedServers = new ArrayList<>();

    public Map<String, String> serverNameMap = new HashMap<>();

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
    }

    public void load() {
        //加载冷却时间
        coolDownTime = config.getInt("times.cooldown");
        //加载过期时间
        expiryTime = config.getInt("times.expiry");
        //加载不能使用邀请指令的服务器
        blockedServers = config.getStringList("blockedServers");
        //加载消息
        loadMessages();
        //加载自定义的服务器显示名
        loadServerNames();
    }

    private void loadMessages() {
        for (Messages message : Messages.values()) {
            message.setMessage(config.getString(message.getConfigPath()));
        }
    }

    private void loadServerNames() {
        serverNameMap.clear();
        Configuration serverSection = config.getSection("servers");
        for (String serverInfoName : serverSection.getKeys()) {
            String serverNickName = Color.s(serverSection.getString(serverInfoName));
            serverNameMap.put(serverInfoName, serverNickName);
        }
    }


}
