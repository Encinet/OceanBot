package org.encinet.oceanbot.QQ.event;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.OceanBot;

import java.util.Objects;

import static org.encinet.oceanbot.file.Config.MainGroup;

public class Friend extends SimpleListenerHost {

    @EventHandler
    public void Players(FriendMessageEvent e) {
        net.mamoe.mirai.contact.Friend friend = e.getFriend();
        long friendId = friend.getId();
        if (Objects.requireNonNull(OceanBot.core.getBot().getGroup(MainGroup)).contains(friendId)) {
            String message = e.getMessage().contentToString();

            for (String n : Config.commandPrefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    String answer = OceanBot.occommand.execute(message.substring(1), friendId, false);
                    if (!answer.equals("")) {
                        friend.sendMessage(answer);
                    }
                    break;
                }
            }
        }
    }
}
