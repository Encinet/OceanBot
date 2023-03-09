package org.encinet.oceanbot.common;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Whitelist;
import org.encinet.oceanbot.until.Process;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Whois {
    public static String core(String on, Long SenderID) {
        if (on == null) {// 查自己
            if (Whitelist.getBind(SenderID) != null) {
                return search(Whitelist.getBind(SenderID));
            } else {
                return "你还没有绑定游戏ID呢";
            }
        }
        long qqNum = Process.stringToQBind(on);
        return qqNum == 0 ? "查无此人" : search(Whitelist.getBind(qqNum));
    }

    private static String search(UUID uuid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        boolean online = player.isOnline();
        if (!player.hasPlayedBefore()) {
            return "此玩家尚未进服";
        } else {
            String f;
            if (online) {
                Player o = Bukkit.getPlayer(uuid);
                assert o != null;
                f = "在线 " + o.getPing() + "ms";
            } else {
                f = player.isBanned() ? "封禁" : "离线";
            }
            return "ID: " + Bukkit.getOfflinePlayer(uuid).getName() + " " + f + "\n" +
                    "UUID: " + uuid + "\n" +
                    "QQ: " + Whitelist.getBind(uuid) + "\n" +
                    "经济: " + OceanBot.econ.getBalance(player) + "米币\n" +
                    "加入时间: " + dateFormat.format(player.getFirstPlayed()) + "\n" +
                    "最近游玩: " + dateFormat.format(player.getLastSeen());
        }
    }
}
