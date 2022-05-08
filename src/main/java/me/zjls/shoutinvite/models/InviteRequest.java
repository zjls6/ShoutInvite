package me.zjls.shoutinvite.models;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class InviteRequest {

    private ProxiedPlayer inviter;

    private ServerInfo serverInfo;

    private UUID token;

    private Long inviteTime;

    public InviteRequest(ProxiedPlayer inviter) {
        this.inviter = inviter;
        serverInfo = inviter.getServer().getInfo();

        token = UUID.randomUUID();
        inviteTime = System.currentTimeMillis();
    }

}
