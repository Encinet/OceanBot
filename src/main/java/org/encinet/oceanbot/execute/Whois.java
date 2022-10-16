package org.encinet.oceanbot.execute;

import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Whois {
    public static String core(String on, Long SenderID) {
        if (on == null) {// 查自己
            if (MiraiMC.getBind(SenderID) != null) {
                return search(MiraiMC.getBind(SenderID));
            } else {
                return "你还没有绑定游戏ID呢";
            }
        }
        try {
            // 尝试为QQ号
            if (on.startsWith("@")) {
                long num = Long.parseLong(on.substring(1));
                if (MiraiMC.getBind(num) != null) {
                    return search(MiraiMC.getBind(num));
                } else {
                    return "查无此人";
                }
            } else if (MiraiMC.getBind(Long.parseLong(on)) != null) {
                return search(MiraiMC.getBind(Long.parseLong(on)));
            } else {
                return "查无此人";
            }
        } catch (NumberFormatException e) {
            // 尝试为游戏ID
            UUID uuid = Bukkit.getOfflinePlayer(on).getUniqueId();
            if (MiraiMC.getBind(uuid) != 0) {
                return search(uuid);
            } else {
                return "查无此人";
            }
        }
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
f = "在线 " + o.getPing() + "ms";
        } else {
f = player.isBanned() ? "封禁" : "离线";
}
return "ID: " + Bukkit.getOfflinePlayer(uuid).getName() + " " + f + "\n" +
                "UUID: " + uuid + "\n" +
                "QQ: " + MiraiMC.getBind(uuid) + "\n" +
                "加入时间: " + dateFormat.format(player.getFirstPlayed()) + "\n" +
                "最近游玩: " + dateFormat.format(player.getLastSeen())
                ;
        }
    }
}
