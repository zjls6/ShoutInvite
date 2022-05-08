package me.zjls.shoutinvite.commands;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class InviteTeleport extends Command {

    private Main plugin;

    public InviteTeleport(Main plugin) {
        super("itp");
        this.plugin = plugin;
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


    }
}
