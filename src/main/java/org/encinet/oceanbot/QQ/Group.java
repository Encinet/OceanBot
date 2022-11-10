package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiBotInvitedJoinGroupRequestEvent;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static org.encinet.oceanbot.Config.*;

public class Group implements Listener {
    private static final Map<Long, Integer> tiger = new ConcurrentHashMap<>();// 线程安全
    private static long yuukLastTime = 0;
    private static final Random random = new Random();
    private static final String[] study = {"滚去学习", "火速滚去学习", "踏马的，想读职专？", "rnm, 快去学习", "建议主播火速滚去学习"};

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
        long senderID = e.getSenderID();
        long groupID = e.getGroupID();

        if (!inGroup(groupID)) {
            return;
        }
        if (message.length() > 1) {
            for (String n : Config.prefix) {// 遍历前缀数组
                if (message.startsWith(n)) {// 如果开头符合
                    String answer = Function.on(message, senderID);
                    if (!answer.equals("")) {
                        MiraiBot.getBot(BotID).getGroup(groupID)
                                .sendMessageMirai(answer);
                    }
                    return;
                }
            }
            for (String n : Config.chatPrefix) {
                // 群向服发送消息
                if (message.startsWith(n) && Objects.equals(groupID, MainGroup)) {
                    String text = message.substring(1);

                    UUID bind = MiraiMC.getBind(senderID);
                    String hoverName = "§7QQ: §3" + String.valueOf(senderID) + "\n" +
                            "§7绑定ID: §3" + (bind == null ? "§e尚未绑定" : Bukkit.getOfflinePlayer(bind).getName());
                    final TextComponent textComponent = Component.text("")
                            .append(Component.text("§8[§cQQ§8]").hoverEvent(HoverEvent.showText(Component.text("""
                                    §8| §b这是一条从QQ群发来的消息
                                    §8| §b消息开头为#可互通

                                    §a➥ §b点击回复""")))
                                    .clickEvent(ClickEvent.suggestCommand("#")))
                            .append(Component.text(e.getSenderName()).color(NamedTextColor.YELLOW)
                                    .hoverEvent(HoverEvent.showText(Component.text(hoverName)))
                                    .clickEvent(ClickEvent.runCommand("/oc !whois " + senderID)))
                            .append(Component.text(": ").color(NamedTextColor.GRAY))
                            .append(Component.text(text));
                    Bukkit.getServer().sendMessage(textComponent);
                    return;
                }
            }
        }
        // 关键词检测
        if (Config.recallEnable) {
            String m = message.toLowerCase();
            for (String n : Config.recallText) {
                if (m.equals(n) || m.contains(n)) {
                    if (e.getBotPermission() > e.getSenderPermission()) {
                    e.recall();
                    tAdd(senderID);
                    if (tiger.get(senderID) >= Config.recallMuteValue) {
                        MiraiNormalMember mem = e.getGroup().getMember(senderID);
                        if (!mem.isMuted()) {
                            mem.setMute(Config.recallMuteTime);
                        }
                        tiger.remove(senderID);
                    }
                } else {
                    MiraiBot.getBot(BotID).getGroup(groupID)
                                            .sendMessageMirai("[mirai:at:" + senderID + "] 建议撤回");
                }
                    return;
                }
            }
        }
        // YuuK
        if (Objects.equals(senderID, 2704804982l)) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - yuukLastTime) >= 60000l) {
                yuukLastTime = nowTime;
                MiraiBot.getBot(BotID).getGroup(groupID)
                                        .sendMessageMirai("[mirai:at:2704804982] " + study[random.nextInt(study.length)]);
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
        if (!nick.contains(name)) {
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

    // 同意管理的邀请
    @EventHandler
    public void invite(MiraiBotInvitedJoinGroupRequestEvent e) {
        for (long n : admin) {
            if (Objects.equals(n, e.getInvitorID())) {
                e.accept();
                return;
            }
        }
    }

    // 触发关键词计数
    protected void tAdd(long qq) {
        if (tiger.containsKey(qq)) {
            int now = tiger.get(qq) + 1;
            tiger.put(qq, now);
        } else {
            tiger.put(qq, 1);
        }
    }
}
