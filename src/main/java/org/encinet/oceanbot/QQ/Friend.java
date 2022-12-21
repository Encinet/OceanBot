package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.execute.Function;

import static org.encinet.oceanbot.Config.*;

public class Friend implements Listener {

    @EventHandler
    public void Players(MiraiFriendMessageEvent e) {
        if (MiraiBot.getBot(BotID).getGroup(MainGroup).contains(e.getSenderID())) {
            String message = e.getMessage();

            command: for (String n : Config.commandPrefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    String answer = Function.on(message.substring(1), e.getSenderID());
                    if (!answer.equals("")) {
                        e.sendMessageMirai(answer);
                    }
                    break command;
                }
            }
        }
    }
}
