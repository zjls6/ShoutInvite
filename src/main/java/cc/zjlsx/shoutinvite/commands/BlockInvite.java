package cc.zjlsx.shoutinvite.commands;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.UUID;

public class BlockInvite extends Command {
    private final Main plugin;

    public BlockInvite(Main plugin) {
        super("hhpb");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(Messages.Run_In_Console.getMessage()));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        UUID uuid = player.getUniqueId();

        List<UUID> blockInvitePlayers = plugin.getBlockInvitePlayers();
        if (blockInvitePlayers.contains(uuid)) {
            blockInvitePlayers.remove(uuid);
            player.sendMessage(new TextComponent(Messages.Unblock_Invite_Message.getMessage()));
        } else {
            blockInvitePlayers.add(uuid);
            player.sendMessage(new TextComponent(Messages.Block_Invite_Message.getMessage()));
        }
    }
}
