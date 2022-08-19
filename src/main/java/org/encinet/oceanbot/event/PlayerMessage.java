package org.encinet.oceanbot.event;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.encinet.oceanbot.Config.*;

public class PlayerMessage implements Listener {

    @EventHandler
    public void playerMessage(AsyncChatEvent e) {
        String message = PlainTextComponentSerializer.plainText().serialize(e.message());
        for (String n : chatPrefix) {
            if (message.startsWith(n)) {
                String formatText = serverToQQ
                        .replace("$[player]", e.getPlayer().getName())
                        .replace("$[message]", message.substring(1));
                MiraiBot.getBot(BotID).getGroup(GroupID)
                        .sendMessageMirai(PlaceholderAPI.setPlaceholders(e.getPlayer(), formatText));
            }
        }
    }
}
