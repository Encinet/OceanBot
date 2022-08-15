package org.encinet.oceanbot.event;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.encinet.oceanbot.until.Data;
import org.encinet.oceanbot.until.Verify;

import static org.encinet.oceanbot.QQ.Bind.code;
import static org.encinet.oceanbot.Config.*;

public class PlayerLogin implements Listener {
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        boolean allow = false;
        long binder = MiraiMC.getBind(e.getUniqueId());
        if (binder != 0) {
            if (MiraiBot.getBot(BotID).getGroup(GroupID).contains(binder)) {
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
            code.put(verify, new Data(e.getUniqueId()));

            String message = noWhiteKick.replace("%verify%", verify);
            e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    ChatColor.translateAlternateColorCodes('&', message)
            );
        }
    }
}
