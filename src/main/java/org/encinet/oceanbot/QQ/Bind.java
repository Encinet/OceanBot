package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.encinet.oceanbot.until.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.encinet.oceanbot.Config.*;

public class Bind {
    public static final Map<String, Data> code = new HashMap<>();

    public static String qqGroup(String text, Long qq) {
        if (MiraiMC.getBind(qq) != null) {
            code.remove(text);
            return "你已经绑定过了";
        } else if ((System.currentTimeMillis() - code.get(text).getTime()) >= 600000) {// 10分钟
            code.remove(text);
            return "验证码已过期";
        }
        MiraiMC.addBind(code.get(text).getUUID(), qq);
        Bukkit.getServer().getWhitelistedPlayers();

        String nick = MiraiBot.getBot(BotID).getGroup(MainGroup).getMember(qq).getNick();
        String name = code.get(text).getName();
        if (!Objects.equals(name, nick) && !nick.endsWith("(" + name + ")")) {
            MiraiBot.getBot(BotID).getGroup(MainGroup).getMember(qq).setNameCard(nick + "(" + name + ")");
        }

        code.remove(text);

        return "[mirai:at:" + qq + "] " + "绑定成功, 你现在可以进服游玩啦";
    }
}


