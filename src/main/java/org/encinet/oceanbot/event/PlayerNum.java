package org.encinet.oceanbot.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;

import static org.encinet.oceanbot.Config.*;

public class PlayerNum implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        chance();
    }
    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        chance();
    }

    /**
     * 执行群昵称更改
     */
    public static void chance() {
        int online = Bukkit.getOnlinePlayers().size();
        int max = Bukkit.getMaxPlayers();
        String now = BotNick + " -" + online + "/" + max;

        MiraiNormalMember member = MiraiBot.getBot(BotID).getGroup(MainGroup).getMember(BotID);
        if (!now.equals(member.getNick())) {
            member.setNameCard(now);
        }
    }
}
