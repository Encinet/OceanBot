package org.encinet.oceanbot.QQ;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.execute.Function;

import java.util.Objects;

import static org.encinet.oceanbot.Config.MainGroup;

public class FriendProcess extends SimpleListenerHost {

    @EventHandler
    public void Players(FriendMessageEvent e) {
        Friend friend = e.getFriend();
        long friendId = friend.getId();
        if (Objects.requireNonNull(OceanBot.core.getBot().getGroup(MainGroup)).contains(friendId)) {
            String message = e.getMessage().contentToString();

            for (String n : Config.commandPrefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    String answer = Function.on(message.substring(1), friendId);
                    if (!answer.equals("")) {
                        friend.sendMessage(answer);
                    }
                    break;
                }
            }
        }
    }
}
