package cc.zjlsx.shoutinvite;

import cc.zjlsx.shoutinvite.commands.InviteTeleport;
import cc.zjlsx.shoutinvite.commands.Shout;
import cc.zjlsx.shoutinvite.commands.ShoutInvite;
import cc.zjlsx.shoutinvite.enums.Messages;
import cc.zjlsx.shoutinvite.listeners.PlayerJoin;
import cc.zjlsx.shoutinvite.models.InviteRequest;
import cc.zjlsx.shoutinvite.storage.ConfigManager;
import cc.zjlsx.shoutinvite.storage.MySQL;
import cc.zjlsx.shoutinvite.storage.SQLGetter;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Main extends Plugin {
    public MySQL SQL;
    public SQLGetter data;

    public ConfigManager configManager;

    public List<InviteRequest> inviteRequests = new ArrayList<>();

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
        getProxy().getPluginManager().registerCommand(this, new ShoutInvite(this));
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
