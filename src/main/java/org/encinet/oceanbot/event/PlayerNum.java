package org.encinet.oceanbot.event;

import net.mamoe.mirai.contact.NormalMember;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.encinet.oceanbot.OceanBot;

import java.util.Objects;

import static org.encinet.oceanbot.file.Config.*;

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
        String now = BotNick + " - " + online + "/" + max;

        NormalMember member = Objects.requireNonNull(OceanBot.core.getBot().getGroup(MainGroup)).getBotAsMember();
        if (!now.equals(member.getNick())) {
            member.setNameCard(now);
        }
    }
}
