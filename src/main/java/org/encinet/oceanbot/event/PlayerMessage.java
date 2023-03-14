package org.encinet.oceanbot.event;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Whitelist;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static org.encinet.oceanbot.file.Config.*;

public class PlayerMessage implements Listener {

    @EventHandler
    public void playerMessage(AsyncChatEvent e) {
        String get = PlainTextComponentSerializer.plainText().serialize(e.message());
        for (String n : chatPrefix) {
            if (get.startsWith(n)) {
                String message = atPlayer(get.substring(1));
                String formatText = serverToQQ
                        .replace("$[player]", e.getPlayer().getName())
                        .replace("$[message]", message);
                OceanBot.core.getBot().getGroup(MainGroup).sendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), formatText));
                return;
            }
        }
    }

    /**
     * At字符串替换
     *
     * @param text 消息
     * @return 格式化后消息
     */
    private String atPlayer(String text) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (text.toLowerCase().contains(player.getName().toLowerCase())) {
                UUID uuid = player.getUniqueId();
                long qqNum = OceanBot.whitelist.getBind(uuid).qq();

                text = text.replace("@" + player.getName(), "[mirai:at:" + qqNum + "]");
                text = text.replace(player.getName(), "[mirai:at:" + qqNum + "]");
            }
        }
        return text;
    }
}
