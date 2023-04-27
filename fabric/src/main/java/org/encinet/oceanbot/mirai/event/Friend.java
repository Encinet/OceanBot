package org.encinet.kitebot.mirai.event;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.encinet.kitebot.common.command.sender.QQFriendSender;
import org.encinet.kitebot.file.Config;
import org.encinet.kitebot.KiteBot;

import java.util.Objects;

public class Friend extends SimpleListenerHost {

    @EventHandler
    public void Players(FriendMessageEvent e) {
        net.mamoe.mirai.contact.Friend friend = e.getFriend();
        long friendId = friend.getId();
        if (Objects.requireNonNull(KiteBot.core.getBot().getGroup(Config.MainGroup)).contains(friendId)) {
            String message = e.getMessage().contentToString();

            for (String n : Config.commandPrefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    OceanBot.occommand.execute(new QQFriendSender(friend), message.substring(1));
                    break;
                }
            }
        }
    }
}
