package me.zjls.shoutinvite.Listeners;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(ServerConnectedEvent e) {
        if (Main.SQL.isConnected()) {
            if (Main.data.createPlayer(e.getPlayer())) {
                Main.instance.getLogger().info("§a已创建玩家 " + e.getPlayer().getName() + " 的表项");
            }
        }
    }
}
