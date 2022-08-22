package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.encinet.oceanbot.Config.BotID;

public class Friend implements Listener {

    @EventHandler
    public void Players(MiraiFriendMessageEvent e) {
        MiraiBot.getBot(BotID).getFriend(e.getSenderID()).sendMessageMirai(Function.on(e.getMessage(), e.getSenderID()));
    }
}
