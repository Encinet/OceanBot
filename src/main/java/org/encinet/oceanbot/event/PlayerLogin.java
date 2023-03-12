package org.encinet.oceanbot.event;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.mamoe.mirai.contact.Group;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Whitelist;
import org.encinet.oceanbot.command.Maintenance;
import org.encinet.oceanbot.until.Data;
import org.encinet.oceanbot.until.Verify;

import static org.encinet.oceanbot.file.Config.*;
import static org.encinet.oceanbot.QQ.Bind.code;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        if (Maintenance.enable) {
            e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    LegacyComponentSerializer.legacyAmpersand().deserialize("维护模式"));
        }

        boolean allow = false;
        long binder = Whitelist.getBindQQ(e.getUniqueId());
        if (binder != 0) {
            Group members = OceanBot.core.getBot().getGroup(MainGroup);

            if (members == null) {
                e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                        LegacyComponentSerializer.legacyAmpersand().deserialize("服务器启动中, 请稍后再尝试进入"));
                return;
            } else if (members.contains(binder)) {
                allow = true;
            }
        }
        if (allow) {
            e.allow();
        } else {
            String verify;
            do {
                verify = Verify.get();
            } while (code.containsKey(verify));
            code.put(verify, new Data(e.getUniqueId(), e.getName()));

            String message = noWhiteKick.replace("%verify%", verify);
            e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ChatColor.translateAlternateColorCodes('&', message)));
        }
    }
}
