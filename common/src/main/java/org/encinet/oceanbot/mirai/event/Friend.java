package org.encinet.oceanbot.mirai.event;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.encinet.oceanbot.common.occommand.sender.QQFriendSender;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.OceanBot;

import java.util.Objects;

public class Friend extends SimpleListenerHost {

    @EventHandler
    public void Players(FriendMessageEvent e) {
        net.mamoe.mirai.contact.Friend friend = e.getFriend();
        long friendId = friend.getId();
        if (Objects.requireNonNull(OceanBot.core.getBot().getGroup(OceanBot.config.MainGroup)).contains(friendId)) {
            String message = e.getMessage().contentToString();

            for (String n : OceanBot.config.commandPrefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    OceanBot.occommand.execute(new QQFriendSender(friend), message.substring(1));
                    break;
                }
            }
        }
    }
}
