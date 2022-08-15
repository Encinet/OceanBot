package org.encinet.oceanbot.event;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.encinet.oceanbot.Config;

import java.util.Random;

public class PlayerNumber implements Listener {

    public static final String[] noOne = {
            "米客 | 欢迎你的到来", "米客 | 纯净生存服务器", "米客 | 快乐养老"
    };

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (Config.gnc) {
            change();
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        if (Config.gnc) {
            change();
        }
    }

    public static void change() {
        int now = Bukkit.getServer().getOnlinePlayers().toArray().length;
        if (now > 0) {
            MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).setName("米客 | 现在" + now + "人在线");
        } else {
            Random r = new Random();
            MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).setName(noOne[r.nextInt(noOne.length)]);
        }
    }
}
