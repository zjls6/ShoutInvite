package me.zjls.shoutinvite.enums;

public enum Permissions {

    NoCoolDown("shoutinvite.nocooldown");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
