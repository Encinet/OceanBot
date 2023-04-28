package org.encinet.oceanbot.mirai.event;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
import net.mamoe.mirai.message.data.*;
import org.encinet.oceanbot.KiteBot;
import org.encinet.oceanbot.common.command.sender.QQGroupSender;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.mirai.consciousness.CS;
import org.encinet.oceanbot.until.record.BindData;
import org.encinet.oceanbot.until.HttpUnit;
import org.encinet.oceanbot.until.Process;
import org.encinet.oceanbot.until.QQUntil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Group extends SimpleListenerHost {
    private static final Map<Long, Integer> tiger = new ConcurrentHashMap<>();// 线程安全


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        // 向工作群发送报错
        KiteBot.core.getBot().getGroup(Config.LogGroup).sendMessage("Bot Error " + System.currentTimeMillis() + "\n" + exception);
        exception.printStackTrace();
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) { // 可以抛出任何异常, 将在 handleException 处理
        net.mamoe.mirai.contact.Group group = event.getGroup();
        long groupID = group.getId();
        if (!isEnableGroup(groupID)) {
            // is not enanle group
            return;
        }
        
        MessageChain messageChain = event.getMessage();

        String message = messageChain.contentToString();
        String miraiMessage = messageChain.toString();

        Member member = event.getSender();
        long memberID = member.getId();

        if (member instanceof NormalMember normalMember) {
            MemberPermission botPermission = group.getBotPermission();
            MemberPermission memberPermission = member.getPermission();
            
            // 关键词检测
            if (Config.recallEnable && QQUntil.shouldRecall(message)) {
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
                    group.sendMessage(new MessageChainBuilder()
                        .append(new At(memberID))
                        .append(" 发现危险词汇，建议撤回")
                        .build());
                }
                return;
            }
            if (message.length() > 1) {
                // Get quote
                QuoteReply quote = messageChain.get(QuoteReply.Key);
                        if (quote != null) {
                            MessageSource source = quote.getSource();
                            if ("撤回".equals(message) && source.getFromId() == memberID) {
                                MessageSource.recall(source);
                            }
                        }
                // qq command
                for (String n : Config.commandPrefix) {// 遍历前缀数组
                    if (message.startsWith(n)) {// 如果开头符合
                        KiteBot.occommand.execute(new QQGroupSender(memberID, group, messageChain), message.substring(1));
                        break;
                    }
                }
                // message send each other
                for (String n : Config.chatPrefix) {
                    // 群向服发送消息
                    if (message.startsWith(n) && Objects.equals(groupID, MainGroup)) {
                        String text = message.substring(1);

                        UUID bind = OceanBot.whitelist.getBind(memberID).uuid();
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
                        // Bukkit.getServer().sendMessage(textComponent);
                        KiteBot.SERVER.getPlayerManager().broadcast(textComponent);
                        break;
                    }
                }
            }
            // consciousness
            MessageChain cs = CS.enter(message);
            if (cs != null) {
                group.sendMessage(cs);
            }
        }// else匿名
    }

    @EventHandler
    public void join(@NotNull MemberJoinEvent e) {
        net.mamoe.mirai.contact.Group group = e.getGroup();
        if (!Objects.equals(group.getId(), MainGroup)) {
            return;
        }
        MessageChain msg = new MessageChainBuilder()
            .append(new At(e.getMember().getId()))
            .append("\n")
                .append(Config.join)
            .build();
        group.sendMessage(msg);
    }

    @EventHandler
    public void leave(@NotNull MemberLeaveEvent e) {
        net.mamoe.mirai.contact.Group group = e.getGroup();
        if (!Objects.equals(group.getId(), MainGroup)) {
            return;
        }
        long id = e.getMember().getId();
        // 自带是否存在判断
        KiteBot.whitelist.remove(id);
    }

    @EventHandler
    public void cardChange(MemberCardChangeEvent e) {
        net.mamoe.mirai.contact.Group group = e.getGroup();
        long groupID = group.getId();

        NormalMember member = e.getMember();
        long memberID = member.getId();
        String nick = e.getNew();

        BindData bindData = KiteBot.whitelist.getBind(memberID);
        if (!Objects.equals(groupID, MainGroup) || bindData == null) {
            return;
        }

        String name = bindData.name();
        if (name == null) {
            return;
        }
        if (!nick.contains(name)) {
            member.setNameCard(nick + "(" + name + ")");
        } else if (("(" + name + ")").equals(nick)) {
            member.setNameCard(name);
        }
    }

    protected boolean isEnableGroup(Long qq) {
        for (Long num : Config.EnableGroup) {
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

    // 申请加入群事件
    @EventHandler
    public void memberJoinRequest(MemberJoinRequestEvent e) throws IOException, InterruptedException {
        long qq = e.getFromId();
        // 联合封禁
        String body = HttpUnit.get("https://blacklist.baoziwl.com/api.php?qq=" + qq);
        JSONObject data = JSON.parseObject(body);
        int status = data.getInteger("status");
        if (status == 1) {
            String reason = data.getString("text_lite");
            e.reject(true, "联合封禁" + (reason == null ? "" : ":" + reason));
            KiteBot.core.getBot().getGroup(MainGroup).sendMessage("拒绝 " + qq + " 的入群申请\n云黑名单: " + reason);
        }
    }
}
