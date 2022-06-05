package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.enums.Messages;
import cc.zjlsx.shoutinvite.utils.Color;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ConfigManager extends BaseYamlConfig {
    public int coolDownTime;
    public int expiryTime;
    public boolean canBlockedServerSeeMessages;

    public boolean canBlockedServerSeeMessages() {
        return canBlockedServerSeeMessages;
    }

    public List<String> blockedServers = new ArrayList<>();
    public Map<String, String> serverNameMap = new HashMap<>();

    public ConfigManager(Main plugin) {
        super(plugin, "config.yml");
        load();
    }

    public void load() {
        //加载冷却时间
        coolDownTime = config.getInt("times.cooldown");
        //加载过期时间
        expiryTime = config.getInt("times.expiry");
        //加载不能使用邀请指令的服务器
        blockedServers = config.getStringList("blockedServers");
        //加载不能使用邀请指令的服务器是否能看到喊话的消息
        canBlockedServerSeeMessages = config.getBoolean("canBlockedServerSeeMessages");
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
