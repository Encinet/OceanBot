package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberCardChangeEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.execute.Function;

import java.util.Objects;

import static org.encinet.oceanbot.Config.*;

public class Group implements Listener {

    @EventHandler
    public void join(MiraiMemberJoinEvent e) {
        if (!Objects.equals(e.getGroupID(), MainGroup)) {
            return;
        }
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessageMirai("[mirai:at:" + e.getNewMemberID() + "]\n" + Config.join);
    }

    @EventHandler
    public void leave(MiraiMemberLeaveEvent e) {
        if (!Objects.equals(e.getGroupID(), MainGroup)) {
            return;
        }
        long id = e.getTargetID();
        if (MiraiMC.getBind(id) != null) {
            MiraiMC.removeBind(id);
        }
    }

    @EventHandler
    public void Players(MiraiGroupMessageEvent e) {
        String message = e.getMessage();
        if (message.length() < 2 ||!inGroup(e.getGroupID())) {
            return;
        }
        for (String n : Config.prefix) {// 遍历前缀数组
            if (message.startsWith(n)) {// 如果开头符合
                MiraiBot.getBot(BotID).getGroup(e.getGroupID()).sendMessageMirai(Function.on(e.getMessage(), e.getSenderID()));
                return;
            }
        }
        for (String n : Config.chatPrefix) {
            // 群向服发送消息
            if (message.startsWith(n)) {
                String text = e.getMessage().substring(1);
                String formatText = qqToServer
                        .replace("$[nick]", e.getSenderName())
                        .replace("$[qq]", String.valueOf(e.getSenderID()))
                        .replace("$[message]", text);
                Bukkit.getServer().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(ChatColor.translateAlternateColorCodes('&', formatText)));
                return;
            }
        }
    }

    @EventHandler
    public void cardChange(MiraiMemberCardChangeEvent e) {
        if (!Objects.equals(e.getGroupID(), MainGroup) || MiraiMC.getBind(e.getMemberID()) == null) {
            return;
        }

        String nick = e.getNewNick();
        String name = Bukkit.getOfflinePlayer(Objects.requireNonNull(MiraiMC.getBind(e.getMemberID()))).getName();
        if (name == null) {
            return;
        }
        if (!Objects.equals(name, nick) && !nick.endsWith("(" + name + ")")) {
            MiraiBot.getBot(Config.BotID).getGroup(e.getGroupID()).getMember(e.getMemberID()).setNameCard(nick + "(" + name + ")");
        } else if (("(" + name + ")").equals(nick)) {
            MiraiBot.getBot(Config.BotID).getGroup(e.getGroupID()).getMember(e.getMemberID()).setNameCard(name);
        }
    }

    protected boolean inGroup(Long qq) {
        for (Long num : GroupID) {
            if (qq.equals(num)) {
                return true;
            }
        }
        return false;
    }
}
