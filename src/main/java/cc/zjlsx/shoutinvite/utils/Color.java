package cc.zjlsx.shoutinvite.utils;

import net.md_5.bungee.api.ChatColor;

public final class Color {
    public static String s(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
