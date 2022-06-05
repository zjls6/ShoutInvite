package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import cc.zjlsx.shoutinvite.models.Announcement;
import cc.zjlsx.shoutinvite.tasks.AnnouncementTask;
import lombok.Getter;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class AnnouncementManager extends BaseYamlConfig {
    private long interval;
    public List<String> disabledServers = new ArrayList<>();
    private final List<Announcement> announcementList = new LinkedList<>();
    private ScheduledTask announcementTask;

    public AnnouncementManager(Main plugin) {
        super(plugin, "announcements.yml");
        //加载/初始化
        load();
    }

    public void load() {
        //加载公告的间隔时间
        interval = config.getLong("settings.interval");
        //加载不显示公告的服务器
        disabledServers = config.getStringList("settings.disabledServers");
        //加载公告
        loadAnnouncements();
    }

    private void loadAnnouncements() {
        announcementList.clear();

        Configuration announcements = config.getSection("announcements");
        for (String announcementId : announcements.getKeys()) {
            Configuration announcementSection = announcements.getSection(announcementId);

            boolean enabled = announcementSection.getBoolean("enabled", true);
            if (!enabled) {
                continue;
            }
            List<String> messages = announcementSection.getStringList("messages");

            announcementList.add(new Announcement(announcementId, messages));
        }
        //定时发送公告
        scheduleAnnouncements();
    }

    private void scheduleAnnouncements() {
        announcementTask = plugin.getProxy().getScheduler().schedule(plugin, new AnnouncementTask(this), 0, interval, TimeUnit.SECONDS);
    }
}
