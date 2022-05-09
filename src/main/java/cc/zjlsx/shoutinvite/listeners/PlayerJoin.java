package cc.zjlsx.shoutinvite.listeners;

import cc.zjlsx.shoutinvite.Main;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoin implements Listener {

    private Main plugin;

    @EventHandler
    public void onJoin(ServerConnectedEvent e) {
//        if (plugin.getSQL().isConnected()) {
//            if (plugin.getData().createPlayer(e.getPlayer())) {
//                plugin.getLogger().info("§a已创建玩家 " + e.getPlayer().getName() + " 的表项");
//            }
//        }
    }
}
