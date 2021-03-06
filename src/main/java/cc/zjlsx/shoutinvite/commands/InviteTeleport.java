package cc.zjlsx.shoutinvite.commands;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.callbacks.ConnectCallback;
import cc.zjlsx.shoutinvite.enums.Messages;
import cc.zjlsx.shoutinvite.models.InviteRequest;
import cc.zjlsx.shoutinvite.storage.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Optional;

public class InviteTeleport extends Command {

    private final Main plugin;

    private final ConfigManager configManager;

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
        //itp <token> 该命令不允许玩家手动执行，无需帮助提示
        if (args.length != 1) {
            return;
        }
        String token = args[0];

        Optional<InviteRequest> optionalInviteRequest = plugin.getInviteRequests().stream().filter(inviteRequest -> inviteRequest.getToken().toString().equals(token)).findFirst();
        if (!optionalInviteRequest.isPresent()) {
            return;
        }
        InviteRequest inviteRequest = optionalInviteRequest.get();
        //判断该邀请是否过期
        if (System.currentTimeMillis() - inviteRequest.getInviteTime() > configManager.getExpiryTime() * 1000L) {
            player.sendMessage(new TextComponent(Messages.Invitation_Expired.getMessage()));
            return;
        }
        player.connect(inviteRequest.getServerInfo(), new ConnectCallback());
    }
}
