package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.friend.MiraiNewFriendRequestEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.encinet.oceanbot.Config.*;

public class Friend implements Listener {

    @EventHandler
    public void Players(MiraiFriendMessageEvent e) {
        if (MiraiBot.getBot(BotID).getGroup(MainGroup).contains(e.getSenderID())) {
            MiraiBot.getBot(BotID).getFriend(e.getSenderID()).sendMessageMirai(Function.on(e.getMessage(), e.getSenderID()));
        }
    }

    @EventHandler
    public void newFriend(MiraiNewFriendRequestEvent e) {
        if (MiraiBot.getBot(BotID).getGroup(MainGroup).contains(e.getFromID())) {
            e.accept();
        } else e.reject(false);
    }
}
