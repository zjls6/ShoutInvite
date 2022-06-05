package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.utils.Color;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Getter
@Setter
public class BaseYamlConfig {
    public final Main plugin;
    private final String fileName;
    private File configFile;
    public Configuration config;

    public BaseYamlConfig(Main plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        init();
    }

    private void init(){
        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                plugin.getLogger().severe(Color.s("&c创建配置文件夹失败"));
            }
        }

        configFile = new File(dataFolder, fileName);

        //检测配置文件是否存在
        if (!configFile.exists()) {
            //不存在，则复制配置文件
            try (InputStream in = plugin.getResourceAsStream(fileName)) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载配置文件
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(dataFolder, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        File dataFolder = plugin.getDataFolder();
        //加载配置文件
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(dataFolder, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
