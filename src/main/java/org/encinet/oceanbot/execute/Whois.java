package org.encinet.oceanbot.execute;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.encinet.oceanbot.Config.BotID;
import static org.encinet.oceanbot.Config.GroupID;

public class Whois {
    public static void core(String on, Long SenderID) {
        if (on == null) {// 查自己
            if (MiraiMC.getBind(SenderID) != null) {
                search(MiraiMC.getBind(SenderID));
            } else {
                MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("你还没有绑定游戏ID呢");
            }
            return;
        }
        try {
            // 尝试为QQ号
            if (on.startsWith("@")) {
                long num = Long.parseLong(on.substring(1));
                if (MiraiMC.getBind(num) != null) {
                    search(MiraiMC.getBind(num));
                } else {
                    MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("未知的玩家");
                }
            } else if (MiraiMC.getBind(Long.parseLong(on)) != null) {
                search(MiraiMC.getBind(Long.parseLong(on)));
            } else {
                MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("未知的玩家");
            }
        } catch (NumberFormatException e) {
            // 尝试为游戏ID
            UUID uuid = Bukkit.getOfflinePlayer(on).getUniqueId();
            if (MiraiMC.getBind(uuid) != 0) {
                search(uuid);
            } else {
                MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("未知的玩家");
            }
        }
    }

    private static void search(UUID uuid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage(
                "ID: " + Bukkit.getOfflinePlayer(uuid).getName() + " " + (player.isBanned() ? "封禁" : (player.isOnline() ? "在线" : "离线")) + "\n" +
                        "UUID: " + uuid + "\n" +
                        "QQ: " + MiraiMC.getBind(uuid) + "\n" +
                        "加入时间: " + dateFormat.format(player.getFirstPlayed()) + "\n" +
                        "最近游玩: " + dateFormat.format(player.getLastSeen()) + "\n"
        );
    }
}
