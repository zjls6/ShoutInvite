package cc.zjlsx.shoutinvite.utils;

import litebans.api.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LiteBansAddon {
    public static long getLast7DayBans() {
        String query = "SELECT COUNT(*) FROM {bans} where time>?";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setLong(1, System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLast7DayBansByConsole() {
        String query = "SELECT COUNT(*) FROM {bans} where time>? and banned_by_uuid=?";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setLong(1, System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
            st.setString(2, "CONSOLE");
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLast7DayBansByPlayer() {
        return getLast7DayBans() - getLast7DayBansByConsole();
    }

    public static String parse(String msg) {
        return msg.replace("%litebans_bans_7%", String.valueOf(getLast7DayBans()))
                .replace("%litebans_bans_7_console%", String.valueOf(getLast7DayBansByConsole()))
                .replace("%litebans_bans_7_player%", String.valueOf(getLast7DayBansByPlayer()));
    }
}
