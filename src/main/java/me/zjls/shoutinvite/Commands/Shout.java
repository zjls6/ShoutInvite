package me.zjls.shoutinvite.Commands;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;

public class Shout extends Command {



    public Shout() { super("hh","","lb"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.getServer().getInfo().getName().equalsIgnoreCase("Login")){
                return;
            }
            if (args.length == 0){
                p.sendMessage(new TextComponent("§c用法： /hh <消息>"));
            }else if (args.length > 1) {
                p.sendMessage(new TextComponent("§c用法： /hh <消息>"));
            }else {
                String serverName = p.getServer().getInfo().getName();
                String playerName = p.getName();
                Collection<ProxiedPlayer> players = Main.instance.getProxy().getPlayers();
                TextComponent shoutMessage = new TextComponent(Main.config.getString("message.shout-format")
                                .replaceAll("%server%", serverName)
                                .replaceAll("%player%", playerName)
                                .replaceAll("%message%", args[0]).replaceAll("&","§"));
                TextComponent inviteMessage = new TextComponent(Main.getConfig().getString("message.invite-format").replaceAll("&","§"));
                inviteMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/stp " + serverName));
                if (args[0].contains("来")){
                    shoutMessage.addExtra(inviteMessage);
                }
                players.forEach(target -> target.sendMessage(shoutMessage));
            }
        }else{
            sender.sendMessage(new TextComponent(Main.config.getString("message.run-in-console").replaceAll("&","§")));
        }
    }
}
