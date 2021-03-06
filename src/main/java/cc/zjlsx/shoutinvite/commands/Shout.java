package cc.zjlsx.shoutinvite.commands;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.enums.Messages;
import cc.zjlsx.shoutinvite.models.InviteRequest;
import cc.zjlsx.shoutinvite.storage.ConfigManager;
import cc.zjlsx.shoutinvite.utils.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;
import java.util.stream.Collectors;

public class Shout extends Command {

    private final Main plugin;
    private final ConfigManager configManager;
    Map<UUID, Long> playerCooldownMap = new HashMap<>();

    public Shout(Main plugin) {
        super("hh", "");
        this.plugin = plugin;
        configManager = plugin.getConfigManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(Messages.Run_In_Console.getMessage()));
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        //检查是否在不允许传送的服务器中
        if (isInBlockedServer(p)) {
            p.sendMessage(new TextComponent(Messages.In_Blocked_Server.getMessage()));
            return;
        }

        if (args.length == 0) {
            p.sendMessage(new TextComponent(Color.s("&c用法： /hh <消息>")));
            return;
        }
        if (playerCooldownMap.containsKey(p.getUniqueId())) {
            //玩家在HashMap中
            Long time = playerCooldownMap.get(p.getUniqueId());
            if (time > System.currentTimeMillis()) {
                //他们还在冷却当中（还有剩余时间）
                long timeLeft = (time - System.currentTimeMillis()) / 1000;
                p.sendMessage(new TextComponent(Messages.In_CoolDown.getMessage().replace("%timeleft%", String.valueOf(timeLeft))));
                return;
            }
        }

        ServerInfo serverInfo = p.getServer().getInfo();
        String serverName = serverInfo.getName();
        String playerName = p.getName();

        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(" ").append(arg);
        }
        String msg = sb.toString();

        Collection<ProxiedPlayer> players = plugin.getProxy().getPlayers();

//        if (msg.contains("&") && msg.length() != 1) {
//            if (plugin.getData().delColors(p.getUniqueId(), 1)) {
//                msg = Color.s(msg);
//            } else {
//                p.sendMessage(new TextComponent(Color.s("&c您的彩色喇叭不足！")));
//                return;
//            }
//        }
        TextComponent inviteMessage = new TextComponent(Messages.Invite_Message_Format.getMessage()
                .replace("%server%", configManager.getServerNameMap().getOrDefault(serverName, serverName))
                .replace("%players%", String.valueOf(serverInfo.getPlayers().size()))
                .replace("%player%", playerName)
                .replace("%message%", msg));

        InviteRequest inviteRequest = new InviteRequest(p);
        plugin.getInviteRequests().add(inviteRequest);

        inviteMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/itp " + inviteRequest.getToken()));
        inviteMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Messages.Invite_Hover_Format.getMessage()
                .replace("%server%", configManager.getServerNameMap().getOrDefault(serverName, serverName))
                .replace("%players%", String.valueOf(serverInfo.getPlayers().size()))
                .replace("%player%", playerName)
                .replace("%message%", msg))));

        //自己能看到，屏蔽的人看不到消息
        List<ProxiedPlayer> canSeePlayers = players.stream().filter(player -> !plugin.getBlockInvitePlayers().contains(player.getUniqueId())).collect(Collectors.toList());
        if (!canSeePlayers.contains(p)) {
            canSeePlayers.add(p);
        }
        if (configManager.canBlockedServerSeeMessages()) {
            canSeePlayers.forEach(target -> target.sendMessage(inviteMessage));
        } else {
            canSeePlayers.stream().filter(player -> !isInBlockedServer(player)).collect(Collectors.toList()).forEach(player -> player.sendMessage(inviteMessage));
        }

//        TextComponent inviteMessage = new TextComponent(plugin.getConfig().getString("message.invite-format").replace("&", "§"));

//        if (args[0].contains("来")) {
//            if (plugin.getData().delInvites(p.getUniqueId(), 1)) {
//                shoutMessage.addExtra(inviteMessage);
//            } else {
//                p.sendMessage(new TextComponent(Color.s("&c您的邀请喇叭不足！")));
//            }
//        }
//        if (plugin.getData().delShouts(p.getUniqueId(), 1)) {
//            players.forEach(target -> target.sendMessage(shoutMessage));
        //添加冷却时间
        int coolDownTime = configManager.getCoolDownTime() * 1000;

        for (String permission : p.getPermissions()) {
            if (permission.startsWith("shoutinvite.cooldown.")) {
                String[] split = permission.split("\\.");
                coolDownTime = Integer.parseInt(split[2]) * 1000;
            }
        }

        playerCooldownMap.put(p.getUniqueId(), System.currentTimeMillis() + coolDownTime);
//        } else {
//            p.sendMessage(new TextComponent(Color.s("&c您的喇叭不足！")));
//        }
    }

    public boolean isInBlockedServer(ProxiedPlayer player) {
        //检查是否在不允许传送的服务器中
        return configManager.getBlockedServers().contains(player.getServer().getInfo().getName());
    }
}
