package org.encinet.oceanbot.QQ;

import io.github.yizhiru.thulac4j.Segmenter;
import kotlin.coroutines.CoroutineContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;
import org.bukkit.Bukkit;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.Whitelist;
import org.encinet.oceanbot.execute.Function;
import org.encinet.oceanbot.until.Process;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.encinet.oceanbot.Config.MainGroup;

public class GroupProcess extends SimpleListenerHost {
    private static final Map<Long, Integer> tiger = new ConcurrentHashMap<>();// 线程安全
    private static long yuukLastTime = 0;
    private static final Random random = new Random();
    private static final String[] study = {"滚去学习", "火速滚去学习", "踏马的，想读职专？", "rnm, 快去学习", "建议主播火速滚去学习"};


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) { // 可以抛出任何异常, 将在 handleException 处理
        MessageChain messageChain = event.getMessage();

        String message = messageChain.contentToString();
        String miraiMessage = messageChain.toString();

        Group group = event.getGroup();
        long groupID = group.getId();

        Member member = event.getSender();
        long memberID = member.getId();

        if (member instanceof NormalMember normalMember) {
            MemberPermission botPermission = group.getBotPermission();
            MemberPermission memberPermission = member.getPermission();

            if (!inGroup(groupID)) {
                return;
            }
            if (message.length() > 1) {
//            File consciousnessAudio = Audio.get(miraiMessage);
//            if (consciousnessAudio != null) {
//                MiraiBot.getBot(BotID).getGroup(groupID)
//                        .sendAudio(consciousnessAudio);
//                Audio audio;
//                try (ExternalResource resource = ExternalResource.create(consciousnessAudio)) {
//
//                }
//                group.sendMessage(audio); // 发送语音消息
//                contact.sendMessage(ExternalResource.uploadAsAudio(/*...*/));
//            }
//            String consciousnessText = Text.get(miraiMessage);
//            if (consciousnessText != null) {
//                group.sendMessage(consciousnessText);
//            }

                for (String n : Config.commandPrefix) {// 遍历前缀数组
                    if (message.startsWith(n)) {// 如果开头符合
                        String answer = Function.on(message.substring(1), memberID);
                        if (!answer.equals("")) {
                            group.sendMessage(answer);
                        }
                        break;
                    }
                }
                for (String n : Config.chatPrefix) {
                    // 群向服发送消息
                    if (message.startsWith(n) && Objects.equals(groupID, MainGroup)) {
                        String text = message.substring(1);

                        UUID bind = Whitelist.getBind(memberID);
                        String hoverName = "§7QQ: §3" + memberID + "\n" +
                                "§7绑定ID: §3" + (bind == null ? "§e尚未绑定" : Bukkit.getOfflinePlayer(bind).getName());
                        final TextComponent textComponent = Component.text("")
                                .append(Component.text("§8[§cQQ§8]").hoverEvent(HoverEvent.showText(Component.text("""
                                                §8| §b这是一条从QQ群发来的消息
                                                §8| §b消息开头为#可互通

                                                §a➥ §b点击回复""")))
                                        .clickEvent(ClickEvent.suggestCommand("#")))
                                .append(Component.text(member.getNameCard()).color(NamedTextColor.YELLOW)
                                        .hoverEvent(HoverEvent.showText(Component.text(hoverName)))
                                        .clickEvent(ClickEvent.runCommand("/oc !whois " + memberID)))
                                .append(Component.text(": ").color(NamedTextColor.GRAY))
                                .append(Component.text(text).hoverEvent(HoverEvent.showText(Component.text(event.getTime()))));
                        Bukkit.getServer().sendMessage(textComponent);
                        break;
                    }
                }
            }
            // 关键词检测
            recallCheck:
            if (Config.recallEnable) {
                String m = message.toLowerCase();
                List<String> words = Segmenter.segment(m);
                for (String n : Config.recallText) {
                    if (words.contains(n)) {
                        if (botPermission.getLevel() > memberPermission.getLevel()) {
                            MessageSource.recall(messageChain);
                            Process.mapCountAdd(tiger, memberID);
                            if (tiger.get(memberID) >= Config.recallMuteValue) {
                                if (!NormalMemberKt.isMuted(normalMember)) {
                                    member.mute(Config.recallMuteTime);
                                }
                                tiger.remove(memberID);
                            }
                        } else {
                            group.sendMessage(new At(memberID) + "建议撤回");
                        }
                        break recallCheck;
                    }
                }
            }
            // YuuK
            if (Objects.equals(memberID, 2704804982L)) {
                long nowTime = System.currentTimeMillis();
                if ((nowTime - yuukLastTime) >= 60000L) {
                    yuukLastTime = nowTime;
                    group.sendMessage(new QuoteReply(messageChain) + study[random.nextInt(study.length)]);
                }
            }
        } else {
            group.sendMessage("匿名个der啊");
        }
    }

    @EventHandler
    public void join(@NotNull MemberJoinEvent e) {
        Group group = e.getGroup();
        if (!Objects.equals(group.getId(), MainGroup)) {
            return;
        }
        group.sendMessage(new At(e.getMember().getId()) + "\n" + Config.join);
    }

    @EventHandler
    public void leave(@NotNull MemberLeaveEvent e) {
        Group group = e.getGroup();
        if (!Objects.equals(group.getId(), MainGroup)) {
            return;
        }
        long id = e.getMember().getId();
        if (Whitelist.getBind(id) != null) {
            Whitelist.remove(id);
        }
    }

    @EventHandler
    public void cardChange(MemberCardChangeEvent e) {
        Group group = e.getGroup();
        long groupID = group.getId();

        NormalMember member = e.getMember();
        long memberID = member.getId();
        String nick = e.getNew();

        if (!Objects.equals(groupID, MainGroup) || Whitelist.getBind(memberID) == null) {
            return;
        }

        String name = Bukkit.getOfflinePlayer(Objects.requireNonNull(Whitelist.getBind(memberID))).getName();
        if (name == null) {
            return;
        }
        if (!nick.contains(name)) {
            member.setNameCard(nick + "(" + name + ")");
        } else if (("(" + name + ")").equals(nick)) {
            member.setNameCard(name);
        }
    }

    protected boolean inGroup(Long qq) {
        for (Long num : Config.GroupID) {
            if (qq.equals(num)) {
                return true;
            }
        }
        return false;
    }

    // 同意管理的邀请
    @EventHandler
    public void invite(BotInvitedJoinGroupRequestEvent e) {
        for (long n : Config.admin) {
            if (Objects.equals(n, e.getInvitorId())) {
                e.accept();
                return;
            }
        }
    }

    // 群聊临时会话
    @EventHandler
    public void tempMessage(GroupTempMessageEvent e) {
        Group group = e.getGroup();
        NormalMember member = e.getSender();
        String message = e.getMessage().contentToString();
        long memberID = member.getId();
        if (group.getId() != Config.MainGroup && group.getMembers().contains(memberID)) {
            return;
        }

        for (String n : Config.commandPrefix) {// 遍历前缀数组
            if (message.startsWith(n)) {// 如果开头符合
                String answer = Function.on(message.substring(1), memberID);
                if (!answer.equals("")) {
                    group.sendMessage(answer);
                }
            }
        }
    }
}
