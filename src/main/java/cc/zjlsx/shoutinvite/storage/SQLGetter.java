package cc.zjlsx.shoutinvite.storage;

import cc.zjlsx.shoutinvite.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Main plugin;
    private String tableName = "shoutinvite";

    public SQLGetter(Main plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;

        try {
            ps = plugin.SQL.getConnection().prepareStatement("Create Table IF Not Exists " + tableName +
                    "(id Int(10) AUTO_INCREMENT,Name Varchar(100),uuid Varchar(100),shouts Int(100),invites Int(100),colors Int(100),Primary Key (id))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createPlayer(ProxiedPlayer p) {
        UUID uuid = p.getUniqueId();
        try {
            if (!plugin.getSQL().isConnected()) {
                plugin.getSQL().connect();
            }

            if (!isExists(uuid)) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT IGNORE INTO " + tableName +
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

    public boolean isExists(UUID uuid) {

        try {
            if (!plugin.getSQL().isConnected()) {
                plugin.getSQL().connect();
            }
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Select * From " + tableName + " Where uuid=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Select shouts From " + tableName + " Where uuid=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set shouts=? Where uuid=?");
            ps.setInt(1, shouts);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addShouts(UUID uuid, int shouts) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set shouts=? Where uuid=?");
            ps.setInt(1, getShouts(uuid) + shouts);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delShouts(UUID uuid, int shouts) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set shouts=? Where uuid=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Select invites From " + tableName + " Where uuid=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set invites=? Where uuid=?");
            ps.setInt(1, invites);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInvites(UUID uuid, int invites) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set invites=? Where uuid=?");
            ps.setInt(1, getInvites(uuid) + invites);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delInvites(UUID uuid, int invites) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set invites=? Where uuid=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Select colors From " + tableName + " Where uuid=?");
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
            try (PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set colors=? Where uuid=?")) {
                ps.setInt(1, colors);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColors(UUID uuid, int colors) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set colors=? Where uuid=?");
            ps.setInt(1, getColors(uuid) + colors);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delColors(UUID uuid, int colors) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("Update " + tableName + " Set colors=? Where uuid=?");
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
