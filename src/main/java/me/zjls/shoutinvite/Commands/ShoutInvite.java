package me.zjls.shoutinvite.Commands;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

public class ShoutInvite extends Command {


    public ShoutInvite() { super("shoutinvite","shoutinvite.admin","shouti"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("reload")){
            try {
                Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Main.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(new TextComponent("§a插件重载成功！"));
        }else{
            sender.sendMessage(new TextComponent("§c/shoutinvite reload"));
        }

    }
}
