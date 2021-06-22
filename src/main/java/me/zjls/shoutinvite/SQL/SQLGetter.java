package me.zjls.shoutinvite.SQL;

import me.zjls.shoutinvite.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private final Main plugin;
    private final String tableName = "shoutinvite";

    public SQLGetter(Main plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;

        try {
            ps = Main.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +
                    "(id INT(10) AUTO_INCREMENT,name VARCHAR(100),uuid VARCHAR(100),shouts INT(100),invites INT(100),colors INT(100),PRIMARY KEY (id))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createPlayer(ProxiedPlayer p) {
        UUID uuid = p.getUniqueId();
        try {
            if (!exists(uuid)) {
                PreparedStatement ps = Main.SQL.getConnection().prepareStatement("INSERT IGNORE INTO " + tableName +
                        " (name,uuid,shouts,invites,colors) VALUES (?,?,?,?,?)");
                ps.setString(1, p.getName());
                ps.setString(2, uuid.toString());
                ps.setInt(3, 100);
                ps.setInt(4, 50);
                ps.setInt(5, 10);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next(); //player is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getShouts(UUID uuid) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT shouts FROM " + tableName + " WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("shouts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setShouts(UUID uuid, int shouts) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET shouts=? WHERE uuid=?");
            ps.setInt(1, shouts);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addShouts(UUID uuid, int shouts) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET shouts=? WHERE uuid=?");
            ps.setInt(1, getShouts(uuid) + shouts);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delShouts(UUID uuid, int shouts) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET shouts=? WHERE uuid=?");
            if (getShouts(uuid) == 0) {
                return false;
            }
            ps.setInt(1, getShouts(uuid) - shouts);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getInvites(UUID uuid) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT invites FROM " + tableName + " WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("invites");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setInvites(UUID uuid, int invites) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET invites=? WHERE uuid=?");
            ps.setInt(1, invites);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInvites(UUID uuid, int invites) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET invites=? WHERE uuid=?");
            ps.setInt(1, getInvites(uuid) + invites);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delInvites(UUID uuid, int invites) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET invites=? WHERE uuid=?");
            if (getInvites(uuid) == 0) {
                return false;
            }
            ps.setInt(1, getInvites(uuid) - invites);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getColors(UUID uuid) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT colors FROM " + tableName + " WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("colors");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setColors(UUID uuid, int colors) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET colors=? WHERE uuid=?");
            ps.setInt(1, colors);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColors(UUID uuid, int colors) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET colors=? WHERE uuid=?");
            ps.setInt(1, getColors(uuid) + colors);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delColors(UUID uuid, int colors) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE " + tableName + " SET colors=? WHERE uuid=?");
            if (getColors(uuid) == 0) {
                return false;
            }
            ps.setInt(1, getColors(uuid) - colors);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}
