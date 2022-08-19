package org.encinet.oceanbot.QQ;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberCardChangeEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.event.PlayerNumber;
import org.encinet.oceanbot.execute.Whois;

import java.util.Arrays;
import java.util.Objects;

import static org.encinet.oceanbot.Config.command;
import static org.encinet.oceanbot.Config.qqToServer;

public class Group implements Listener {

    @EventHandler
    public void join(MiraiMemberJoinEvent e) {
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(Config.join);
    }

    @EventHandler
    public void leave(MiraiMemberLeaveEvent e) {
        long id = e.getTargetID();
        if (MiraiMC.getBind(id) != null) {
            MiraiMC.removeBind(id);
        }
    }

    @EventHandler
    public void Players(MiraiGroupMessageEvent e) {
        for (String n : Config.prefix) {// 遍历前缀数组
            if (e.getMessage().startsWith(n)) {// 如果开头符合
                command(e);// 就开始判断是哪个命令
                return;
            }
        }
        for (String n : Config.chatPrefix) {
            // 群向服发送消息
            if (e.getMessage().startsWith(n)) {
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


    private void command(MiraiGroupMessageEvent e) {
        String[] str = e.getMessage().substring(1).split(" ");
        switch (str[0]) {// 截取首位字符以后的东西
            case "help":
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(
                        "消息前加#可发送到服务器或QQ群\n" +
                                "当前可用指令前缀 " + Arrays.toString(Config.prefix.toArray()) + "\n" +
                                "banlist - 列出封禁玩家\n" +
                                "bind 验证码 - 绑定账号\n" +
                                "command - 查看部分命令帮助\n" +
                                "gnc - 开/关自动更改群名功能(仅管理可用)\n" +
                                "help - 查看帮助\n" +
                                "list - 列出在线玩家\n" +
                                "reload - 重载配置文件(仅管理可用)\n" +
                                "whois 玩家名/QQ - 查询信息\n" +
                                "当前自动更改群名功能状态: " + (Config.gnc ? "开启" : "关闭") + "\n" +
                                "当前版本:" + Config.ver + "\n" +
                                "Made By Encinet");
                break;
            case "list":
                StringBuilder textl = new StringBuilder();
                int online = Bukkit.getServer().getOnlinePlayers().toArray().length;// 在线玩家
                int nl = 0;// 计数器
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {// 变量在线玩家数组
                    nl++;// 计数+1
                    textl.append(p.getName());// 添加玩家名
                    if (nl != online) {// 判断是否是最后一人
                        textl.append(",");
                    }
                }
                String replenish = Config.numMessage.getOrDefault(online, "");// 补充消息
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(
                        "当前在线人数：" + Bukkit.getServer().getOnlinePlayers().size() + "人\n" + textl + replenish);
                break;
            case "banlist":
                StringBuilder text = new StringBuilder();
                int max = Bukkit.getServer().getBannedPlayers().toArray().length;//长度
                int n = 0;// 计数器
                for (OfflinePlayer a : Bukkit.getServer().getBannedPlayers()) {// 遍历被Ban掉的玩家数组
                    n++;// 计数+1
                    text.append(a.getName());// 添加玩家名
                    if (n != max) {
                        text.append(",");
                    }
                }
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("当前被封禁玩家有" + max + "人\n" + text);
                break;
            case "reload":
                int per = e.getSenderPermission();
                if (per == 0) {// 0普通成员 1管理 2群主
                    MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("没有权限");
                } else if (per == 1 || per == 2) {// 逻辑或操作符 一个为真(true)输出为真
                    Config.load();
                    MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("配置文件已重载!");
                }
                break;
            case "gnc":
                int per1 = e.getSenderPermission();
                if (per1 == 0) {
                    MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("没有权限");
                }
                if (per1 == 1 || per1 == 2) {
                    Config.gnc = !Config.gnc;
                    MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID())
                            .sendMessage("自动更改群名功能 " + (Config.gnc ? "开启" : "关闭"));
                    if (Config.gnc) {
                        PlayerNumber.change();
                    }
                }
                break;
            case "bind":
                if (str.length < 2) {
                    MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).sendMessage("你还没有输入验证码");
                } else if (!Bind.code.containsKey(str[1])) {
                    MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).sendMessage("无效验证码");
                } else Bind.qqGroup(str[1], e.getSenderID());
                break;
            case "whois":
                Whois.core(str.length < 2 ? null : str[1], e.getSenderID());
                break;
            case "command":
                StringBuilder sb = new StringBuilder();
                for (String a : command) {
                    sb.append(a).append("\n");
                }
                MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).sendMessage(sb.toString());
                break;
            default:
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("错误的命令");
                break;
        }
    }

    @EventHandler
    public void cardChange(MiraiMemberCardChangeEvent e) {
        if (MiraiMC.getBind(e.getMemberID()) == null) {
            return;
        }

        String nick = e.getNewNick();
        String name = Bukkit.getOfflinePlayer(Objects.requireNonNull(MiraiMC.getBind(e.getMemberID()))).getName();

        if (!Objects.equals(name, nick) && !nick.endsWith("(" + name + ")")) {
            MiraiBot.getBot(Config.BotID).getGroup(Config.GroupID).getMember(e.getMemberID()).setNameCard(nick + "(" + name + ")");
        }
    }
}
