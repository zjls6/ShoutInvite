package cc.zjlsx.shoutinvite.tasks;

import cc.zjlsx.shoutinvite.models.Announcement;
import cc.zjlsx.shoutinvite.storage.AnnouncementManager;
import cc.zjlsx.shoutinvite.utils.Color;
import cc.zjlsx.shoutinvite.utils.LiteBansAddon;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

public class AnnouncementTask implements Runnable {
    private final List<Announcement> announcementList;
    private int index = 0;
    private int time = 0;
    private final AnnouncementManager announcementManager;

    public AnnouncementTask(AnnouncementManager announcementManager) {
        this.announcementManager = announcementManager;
        announcementList = announcementManager.getAnnouncementList();
    }

    @Override
    public void run() {
        if (index == announcementList.size()) {
            index = 0;
        }
        for (ProxiedPlayer player : announcementManager.getPlugin().getProxy().getPlayers()) {
            if (isInDisabledServer(player)) {
                continue;
            }
            for (String message : announcementList.get(index).getMessages()) {
                player.sendMessage(new TextComponent(LiteBansAddon.parse(Color.s(message))));
            }
        }
        index++;
    }

    private boolean isInDisabledServer(ProxiedPlayer player) {
        return announcementManager.getDisabledServers().contains(player.getServer().getInfo().getName());
    }
}
