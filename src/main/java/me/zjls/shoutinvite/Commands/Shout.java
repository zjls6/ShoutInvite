package me.zjls.shoutinvite.Commands;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Shout extends Command {



    public Shout() { super("hh",""); }

    Map<String, Long> cooldowns = new HashMap<>();

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
                if (cooldowns.containsKey(p.getName())){
                    //玩家在HashMap中
                    if (cooldowns.get(p.getName()) > System.currentTimeMillis()){
                        //他们还在冷却当中（还有剩余时间）
                        long timeleft = (cooldowns.get(p.getName()) - System.currentTimeMillis()) / 1000;
                        p.sendMessage("§c你还需等待 §b§l" + timeleft + " §c秒才能使用此指令！");
                        return;
                    }
                }

                //添加冷却时间
                int cooldowntime = 30 * 1000;
                cooldowns.put(p.getName(), System.currentTimeMillis() + cooldowntime);

                ServerInfo serverInfo = p.getServer().getInfo();
                String serverName = serverInfo.getName();
                int players_num = serverInfo.getPlayers().size();
                String playerName = p.getName();
                Collection<ProxiedPlayer> players = Main.instance.getProxy().getPlayers();
                TextComponent shoutMessage = new TextComponent(Main.config.getString("message.shout-format")
                                .replaceAll("%server%", serverName)
                                .replaceAll("%players%", String.valueOf(players_num))
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
