package org.encinet.oceanbot.QQ;

import net.mamoe.mirai.contact.NormalMember;
import org.bukkit.Bukkit;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.Whitelist;
import org.encinet.oceanbot.until.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.encinet.oceanbot.Config.MainGroup;

public class Bind {
    public static final Map<String, Data> code = new HashMap<>();

    public static String qqGroup(String text, Long qq) {
        if (Whitelist.getBind(qq) != null) {
            code.remove(text);
            return "你已经绑定过了";
        } else if ((System.currentTimeMillis() - code.get(text).getTime()) >= 600000) {// 10分钟
            code.remove(text);
            return "验证码已过期";
        }
        NormalMember member  = Objects.requireNonNull(OceanBot.core.getBot().getGroup(MainGroup)).getMembers().get(qq);
        Whitelist.write(code.get(text).getUUID(), qq);
        Bukkit.getServer().getWhitelistedPlayers();

        assert member != null;
        String nick = member.getNick();
        String name = code.get(text).getName();
        if (!Objects.equals(name, nick) && !nick.endsWith("(" + name + ")")) {
            member.setNameCard(nick + "(" + name + ")");
        }

        code.remove(text);

        return "[mirai:at:" + qq + "] " + "绑定成功, 你现在可以进服游玩啦";
    }
}


