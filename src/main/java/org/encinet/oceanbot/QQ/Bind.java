package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.encinet.oceanbot.until.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.encinet.oceanbot.Config.BotID;
import static org.encinet.oceanbot.Config.GroupID;

public class Bind {
    public static Map<String, Data> code = new HashMap<>();

    public static void qqGroup(String text, Long qq) {
        if (MiraiMC.getBind(qq) != null) {
            code.remove(text);
            MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("你已经绑定过了");
            return;
        } else if ((System.currentTimeMillis() - code.get(text).getTime()) >= 600000) {// 10分钟
            code.remove(text);
            MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("验证码已过期");
            return;
        }
        MiraiMC.addBind(code.get(text).getUUID(), qq);
        Bukkit.getServer().getWhitelistedPlayers();

        MiraiBot.getBot(BotID).getGroup(GroupID).sendMessage("绑定成功");

        String nick = MiraiBot.getBot(BotID).getGroup(GroupID).getMember(qq).getNick();
        String name = code.get(text).getName();
        if (!Objects.equals(name, nick) && !nick.endsWith("(" + name + ")")) {
            MiraiBot.getBot(BotID).getGroup(GroupID).getMember(qq).setNameCard(nick + "(" + name + ")");
        }

        code.remove(text);
    }
}


