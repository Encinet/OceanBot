package org.encinet.oceanbot.event;

import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static org.encinet.oceanbot.Config.*;

public class PlayerMessage implements Listener {

    @EventHandler
    public void playerMessage(AsyncPlayerChatEvent e) {
        for (String n : chatPrefix) {
            if (e.getMessage().startsWith(n)) {
                String formatText = serverToQQ
                        .replace("$[player]", e.getPlayer().getName())
                        .replace("$[message]", e.getMessage().substring(1));
                MiraiBot.getBot(BotID).getGroup(GroupID)
                        .sendMessageMirai(PlaceholderAPI.setPlaceholders(e.getPlayer(), formatText));
            }
        }
    }
}
