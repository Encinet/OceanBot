package org.encinet.oceanbot.event;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.mamoe.mirai.contact.Group;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.command.Maintenance;
import org.encinet.oceanbot.until.record.BindData;
import org.encinet.oceanbot.until.record.Data;
import org.encinet.oceanbot.until.Verify;

import static org.encinet.oceanbot.common.occommand.commands.bind.codes;
import static org.encinet.oceanbot.file.Config.*;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        if (Maintenance.enable) {
            e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    LegacyComponentSerializer.legacyAmpersand().deserialize("维护模式"));
        }

        boolean allow = false;
        
        BindData bindData = OceanBot.whitelist.getBind(e.getUniqueId());
        if (bindData != null) {
            Group group = OceanBot.core.getBot().getGroup(MainGroup);

            if (group == null) {
                e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                        LegacyComponentSerializer.legacyAmpersand().deserialize("服务器启动中, 请稍后再尝试进入"));
                return;
            } else if (group.contains(bindData.qq())) {
                allow = true;
            }
        }
        if (allow) {
            e.allow();
        } else {
            String verify;
            // 防止重复
            do {
                verify = Verify.get();
            } while (codes.containsKey(verify));
            codes.put(verify, new Data(e.getUniqueId(), e.getName()));

            String message = noWhiteKick.replace("%verify%", verify);
            e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ChatColor.translateAlternateColorCodes('&', message)));
        }
    }
}
