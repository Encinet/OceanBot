package org.encinet.oceanbot.file;

import org.encinet.oceanbot.OceanBot;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import org.encinet.oceanbot.until.record.BindData;

public final class Whitelist {
    String file;
    Connection con;
    
    // 记得测试sql注入
    public Whitelist(String file) throws SQLException {
        this.file = file;
        load();
    }

    public void load() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to load SQLite JDBC class", e);
        }
        OceanBot.logger.warning("SQLite driver loaded");
        this.con = DriverManager.getConnection("jdbc:sqlite:" + file);
        // setup
        try (Statement st = con.createStatement()) {
            // 无绑Q时 QQ为0为管理 1为普通用户
            st.executeUpdate("create table if not exists bind("
                + "UUID VARCHAR(40) PRIMARY KEY NOT NULL,"
                + "Name VARCHAR(20) NOT NULL UNIQUE,"
                + "QQ INT8 NOT NULL UNIQUE DEFAULT 1);");
        }
    }

    /**
    * 添加绑定
    * @param uuid 玩家uuid
    * @param name 玩家id
    * @param qq 玩家QQ号
    * @return 成功即为true
    */
    public boolean add(UUID uuid, String name, long qq) {
        String sql = "INSERT INTO bind (UUID, Name, QQ) VALUES ('?', '?', '?');";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, uuid.toString());
            pst.setString(2, name);
            pst.setLong(3, qq);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // -------
    // 获取绑定
    // -------
    /**
    * 通过玩家uuid查询玩家绑定信息
    * @param uuid 玩家uuid
    * @return 查询到的信息 null是查不到或出现错误
    */
    public BindData getBind(UUID uuid) {
        String sql = "SELECT * FROM bind WHERE UUID=?;";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, uuid.toString());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UUID rsUUID = UUID.fromString(rs.getString("UUID"));
                    String rsName = rs.getString("Name");
                    long rsQQ = rs.getLong("QQ");
                    return new BindData(rsUUID, rsName, rsQQ);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
    * 通过玩家id查询玩家绑定信息
    * @param name 玩家id
    * @return 查询到的信息 null是查不到或出现错误
    */
    public BindData getBind(String name) {
        String sql = "SELECT * FROM bind WHERE Name=?;";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UUID rsUUID = UUID.fromString(rs.getString("UUID"));
                    String rsName = rs.getString("Name");
                    long rsQQ = rs.getLong("QQ");
                    return new BindData(rsUUID, rsName, rsQQ);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
    * 通过玩家id查询玩家绑定信息
    * 支持模糊查询
    * @param name 玩家id 大小写任意 支持通配符模糊查询
    * @return 所有查询到的信息 null是出现错误
    */
    public List<BindData> getBindFuzzy(String name) {
        List<BindData> list = new ArrayList<>();
        String sql = "SELECT * FROM bind WHERE LOWER(Name)=LOWER(?);";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    UUID rsUUID = UUID.fromString(rs.getString("UUID"));
                    String rsName = rs.getString("Name");
                    long rsQQ = rs.getLong("QQ");
                    list.add(new BindData(rsUUID, rsName, rsQQ));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
    * 通过玩家qq查询玩家绑定信息
    * @param qq 玩家qq
    * @return 查询到的信息 null是查不到或出现错误
    */
    public BindData getBind(long qq) {
        String sql = "SELECT * FROM bind WHERE QQ=?;";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, qq);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UUID rsUUID = UUID.fromString(rs.getString("UUID"));
                    String rsName = rs.getString("Name");
                    long rsQQ = rs.getLong("QQ");
                    return new BindData(rsUUID, rsName, rsQQ);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // -------
    // 是否存在
    // -------
    public boolean contains(UUID uuid) {
        return getBind(uuid) != null;
    }
    public boolean contains(String name) {
        return getBind(name) != null;
    }
    public boolean contains(long qq) {
        return getBind(qq) != null;
    }
    
    /**
    * 删除绑定
    * @param qq QQ号
    * @return 成功即为true
    */
    public boolean remove(long qq) {
        String sql = "DELETE FROM bind WHERE QQ=?;";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, qq);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
