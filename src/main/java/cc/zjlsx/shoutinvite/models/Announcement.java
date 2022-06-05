package cc.zjlsx.shoutinvite.models;

import lombok.Data;

import java.util.List;

@Data
public class Announcement {
    private String id;
    private boolean enabled;
    private long interval;
    private List<String> messages;

    public Announcement(String id, List<String> messages) {
        this.id = id;
        this.messages = messages;
    }
}
