package cc.zjlsx.shoutinvite.enums;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public enum Messages {

    Enable("&a插件已开启"),
    Disable("&c插件已关闭"),
    Run_In_Console("&c该命令不能在控制台执行"),
    In_CoolDown("&c你还需等待 &b&l %timeleft% &c秒才能使用此指令！"),
    In_Blocked_Server("&c你不能在该服务器执行此命令！"),
    Invite_Message_Format("&b&l✈ &7 %player% &b&l邀请你加入 &e%server% &b&l房间！并说：%message%\n&6&l点击此信息 &b&l加入！", "format"),
    Reload_Plugin("&a插件配置重载成功");

    private String message;
    private final String configPath;

    Messages(String message) {
        this.message = format(message);
        this.configPath = "messages." + name().toLowerCase().replace("_", "-");
    }

    Messages(String message, String configPath) {
        this.message = format(message);
        this.configPath = "messages." + configPath;
    }

    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void setMessage(String message) {
        this.message = format(message);
    }

    public String getReplacedMessage(ProxiedPlayer player, ServerInfo serverInfo) {
        return message.replace("%player%", player.getName());
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getMessage() {
        return message;
    }

}
