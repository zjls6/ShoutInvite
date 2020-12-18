package me.zjls.shoutinvite.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class STP extends Command {

    public STP() { super("stp"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.getServer().getInfo().getName().equalsIgnoreCase("Login")){
                return;
            }
            if (args.length == 0){
                return;
            }
            ServerInfo target = ProxyServer.getInstance().getServerInfo(args[0]);
            try {
                p.connect(target);
            }catch (NullPointerException e){
                e.printStackTrace();
                p.sendMessage(new TextComponent("§c找不到这个服务器！"));
            }

        }
    }
}
