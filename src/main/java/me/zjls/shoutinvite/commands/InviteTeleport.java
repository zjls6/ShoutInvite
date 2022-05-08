package me.zjls.shoutinvite.commands;

import me.zjls.shoutinvite.Main;
import me.zjls.shoutinvite.enums.Messages;
import me.zjls.shoutinvite.storage.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class InviteTeleport extends Command {

    private Main plugin;

    private ConfigManager configManager;

    public InviteTeleport(Main plugin) {
        super("itp");
        this.plugin = plugin;
        configManager = plugin.getConfigManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
//        if (sender instanceof ProxiedPlayer) {
//
//            if (p.getServer().getInfo().getName().equalsIgnoreCase("登录服")) {
//                return;
//            }
//            if (args.length == 0) {
//                return;
//            }
//            ServerInfo target = ProxyServer.getInstance().getServerInfo(args[0]);
//            try {
//                p.connect(target, new ConnectCallback());
//            } catch (NullPointerException e) {
//                p.sendMessage(new TextComponent(Color.s("&c找不到这个服务器！")));
//            }
//
//        }

        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;

        //检查是否在不允许传送的服务器中
        for (String blockedServer : configManager.getBlockedServers()) {
            if (player.getServer().getInfo().getName().equals(blockedServer)) {
                player.sendMessage(new TextComponent(Messages.In_Blocked_Server.getMessage()));
                return;
            }
        }


    }
}
