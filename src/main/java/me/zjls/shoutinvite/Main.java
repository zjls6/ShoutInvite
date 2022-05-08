package me.zjls.shoutinvite;

import lombok.Getter;
import me.zjls.shoutinvite.commands.InviteTeleport;
import me.zjls.shoutinvite.commands.Shout;
import me.zjls.shoutinvite.enums.Messages;
import me.zjls.shoutinvite.listeners.PlayerJoin;
import me.zjls.shoutinvite.models.InviteRequest;
import me.zjls.shoutinvite.storage.ConfigManager;
import me.zjls.shoutinvite.storage.MySQL;
import me.zjls.shoutinvite.storage.SQLGetter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;

@Getter
public class Main extends Plugin {
    public MySQL SQL;
    public SQLGetter data;

    public ConfigManager configManager;

    public List<InviteRequest> inviteRequests;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // 初始化/加载配置文件
        configManager = new ConfigManager(this);
//        SQL = new MySQL(this);
//        data = new SQLGetter(this);
//        try {
//            SQL.connect();
//        } catch (SQLException e) {
//            getLogger().warning(Color.s("&c&l数据库连接失败"));
//            e.printStackTrace();
//        }
//
//        if (SQL.isConnected()) {
//            getLogger().info(Color.s("&a数据库连接成功"));
//            data.createTable();
//        } else {
//            getLogger().warning("&c&l数据库连接失败");
//        }


//        getProxy().getScheduler().schedule(this, new Task(this), 20, 20, TimeUnit.SECONDS);

        getProxy().getPluginManager().registerCommand(this, new InviteTeleport(this));
//        getProxy().getPluginManager().registerCommand(this, new ShoutInvite(this));
        getProxy().getPluginManager().registerCommand(this, new Shout(this));
        getProxy().getPluginManager().registerListener(this, new PlayerJoin());

        getLogger().info(Messages.Enable.getMessage());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
//        SQL.disConnect();
        getLogger().info(Messages.Disable.getMessage());

    }
}
