package me.zjls.shoutinvite.commands;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

public class ShoutInvite extends Command {

    private Main plugin;

    public ShoutInvite(Main plugin) {
        super("shoutinvite", "", "shouti");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("shoutinvite.admin")) {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    plugin.getConfigManager().setConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(plugin.getConfigManager().getConfigFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                plugin.getConfigManager().reloadMessages();
                plugin.getLogger().info("§a插件重载成功！");
            }

            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new TextComponent("§6/shoutinvite <set/add> <shouts/invites/color> <player> <number>"));
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("shouts")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().setShouts(p.getUniqueId(), Integer.parseInt(args[3]));

                } else if (args[1].equalsIgnoreCase("invites")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().setInvites(p.getUniqueId(), Integer.parseInt(args[3]));
                } else {
                    sender.sendMessage(new TextComponent("§6/shoutinvite set <shouts/invites> <player> <number>"));
                }
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equalsIgnoreCase("shouts")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().addShouts(p.getUniqueId(), Integer.parseInt(args[3]));

                } else if (args[1].equalsIgnoreCase("invites")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().addInvites(p.getUniqueId(), Integer.parseInt(args[3]));
                } else if (args[1].equalsIgnoreCase("colors")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().addColors(p.getUniqueId(), Integer.parseInt(args[3]));
                } else {
                    sender.sendMessage(new TextComponent("§6/shoutinvite add <shouts/invites> <player> <number>"));
                }
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("shouts")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().setShouts(p.getUniqueId(), Integer.parseInt(args[3]));
                } else if (args[1].equalsIgnoreCase("invites")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().setInvites(p.getUniqueId(), Integer.parseInt(args[3]));
                } else if (args[1].equalsIgnoreCase("colors")) {
                    ProxiedPlayer p = plugin.getProxy().getPlayer(args[2]);
                    plugin.getData().setColors(p.getUniqueId(), Integer.parseInt(args[3]));
                } else {
                    sender.sendMessage(new TextComponent("§6/shoutinvite set <shouts/invites> <player> <number>"));
                }
            }
        }
        if (sender instanceof ProxiedPlayer) {
            if (args[0].equalsIgnoreCase("get")) {
                ProxiedPlayer p = (ProxiedPlayer) sender;
                sender.sendMessage(new TextComponent("§a您还有§6 " + plugin.getData().getShouts(p.getUniqueId()) + " §a个喇叭"));
                sender.sendMessage(new TextComponent("§a您还有§6 " + plugin.getData().getInvites(p.getUniqueId()) + " §a个邀请喇叭"));
            }
        }

    }
}
