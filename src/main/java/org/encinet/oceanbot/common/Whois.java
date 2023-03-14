package org.encinet.oceanbot.common;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Whitelist;
import org.encinet.oceanbot.until.Process;
import org.encinet.oceanbot.until.record.BindData;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Whois {
    public static String core(String on, Long SenderID) {
        if (on == null) {// 无参即查自己
            if (OceanBot.whitelist.contains(SenderID)) {
                return search(OceanBot.whitelist.getBind(SenderID));
            } else {
                return "你还没有绑定游戏ID呢";
            }
        } else {
            long qqNum = Process.stringToQBind(on);
            return qqNum == 0 ? "查无此人" : search(OceanBot.whitelist.getBind(qqNum));
        }
    }

    private static String search(BindData data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OfflinePlayer player = Bukkit.getOfflinePlayer(data.uuid());
        boolean online = player.isOnline();
        if (!player.hasPlayedBefore()) {
            return "此玩家尚未进服";
        } else {
            String f;
            if (online) {
                Player o = Bukkit.getPlayer(data.uuid());
                assert o != null;
                f = "在线 " + o.getPing() + "ms";
            } else {
                f = player.isBanned() ? "封禁" : "离线";
            }
            return "ID: " + player.getName() + " " + f + "\n" +
                    "UUID: " + data.uuid() + "\n" +
                    "QQ: " + data.qq() + "\n" +
                    "经济: " + OceanBot.econ.getBalance(player) + "米币\n" +
                    "加入时间: " + dateFormat.format(player.getFirstPlayed()) + "\n" +
                    "最近游玩: " + dateFormat.format(player.getLastSeen());
        }
    }
}
