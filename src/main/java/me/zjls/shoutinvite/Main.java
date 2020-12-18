package me.zjls.shoutinvite;

import me.zjls.shoutinvite.Commands.STP;
import me.zjls.shoutinvite.Commands.Shout;
import me.zjls.shoutinvite.Commands.ShoutInvite;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Main extends Plugin {

    public static Main instance;
    public static File configFile;
    public static Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        configFile = new File(getDataFolder(),"config.yml");

            try {
                if (!configFile.exists()){
                    configFile.createNewFile();
                    config.set("message.shout-format", "&6[喊话]&e[%server%]&b[%player%]&8: &f%message%");
                    config.set("message.invite-format", "&e[点击传送]");
                    config.set("message.run-in-console", "§c该命令不能在控制台执行！");
                    config.set("message.run-in-login", "§c该命令不能在登录服执行！");
                }
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

        getProxy().getPluginManager().registerCommand(this, new STP());
        getProxy().getPluginManager().registerCommand(this, new ShoutInvite());
        getProxy().getPluginManager().registerCommand(this, new Shout());

        getLogger().info("§a插件开启成功");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void setInstance(Main instance){
        Main.instance = instance;
    }
}
