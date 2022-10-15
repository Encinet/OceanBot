package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberCardChangeEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.execute.Function;

import java.util.Objects;
import java.util.UUID;

import static org.encinet.oceanbot.Config.*;

public class Group implements Listener {

    @EventHandler
    public void join(MiraiMemberJoinEvent e) {
        if (!Objects.equals(e.getGroupID(), MainGroup)) {
            return;
        }
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID())
                .sendMessageMirai("[mirai:at:" + e.getNewMemberID() + "]\n" + Config.join);
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
        if (message.length() < 2 || !inGroup(e.getGroupID())) {
            return;
        }
        for (String n : Config.prefix) {// 遍历前缀数组
            if (message.startsWith(n)) {// 如果开头符合
                MiraiBot.getBot(BotID).getGroup(e.getGroupID())
                        .sendMessageMirai(Function.on(e.getMessage(), e.getSenderID()));
                return;
            }
        }
        for (String n : Config.chatPrefix) {
            // 群向服发送消息
            if (message.startsWith(n) && e.getGroupID().equal(Config.MainGroup) ) {
                String text = e.getMessage().substring(1);

                UUID bind = MiraiMC.getBind(e.getSenderID());
                String hoverName = "§7QQ: §3" + String.valueOf(e.getSenderID()) + "\n" +
                        "§7绑定ID: §3" + (bind == null ? "§e尚未绑定" : Bukkit.getOfflinePlayer(bind).getName());
                final TextComponent textComponent = Component.text("")
                        .append(Component.text("§8[§cQQ§8]").hoverEvent(HoverEvent.showText(Component.text("""
                                §8| §b这是一条从QQ群发来的消息
                                §8| §b消息开头为#可互通

                                §a➥ §b点击回复""")))
                                .clickEvent(ClickEvent.suggestCommand("#")))
                        .append(Component.text(e.getSenderName()).color(NamedTextColor.YELLOW)
                                .hoverEvent(HoverEvent.showText(Component.text(hoverName)))
                                .clickEvent(ClickEvent.runCommand("/oc !whois " + e.getSenderID())))
                        .append(Component.text(": ").color(NamedTextColor.GRAY))
                        .append(Component.text(text));
                Bukkit.getServer().sendMessage(textComponent);
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
            MiraiBot.getBot(Config.BotID).getGroup(e.getGroupID()).getMember(e.getMemberID())
                    .setNameCard(nick + "(" + name + ")");
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
