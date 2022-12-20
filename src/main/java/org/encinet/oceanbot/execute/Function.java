package org.encinet.oceanbot.execute;

import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.encinet.oceanbot.ChatGPT;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.QQ.Bind;
import org.encinet.oceanbot.event.PlayerNum;
import org.encinet.oceanbot.until.Conversion;
import org.encinet.oceanbot.until.TopList;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Statistic.*;
import static org.encinet.oceanbot.Config.admin;

public class Function {
    private static final Random random = new Random();

    public static String on(String text, Long qqNum) {
        String rText = "";
        String[] str = text.split(" ");
        switch (str[0]) {// 截取首位字符以后的东西
            case "help", "帮助" -> {
                String adminT = """
                        c,执行 - 执行命令(仅管理可用)
                        chat,聊天 - 开关聊天机器人(仅管理可用)
                        reload,重载 - 重载配置 (仅管理可用)
                        send,发送 - 复读, 支持miraicode (仅管理可用)
                        """;
                rText = ("消息前加#可发送到服务器或QQ群\n" +
                        "当前可用指令前缀 " + Arrays.toString(Config.prefix.toArray()) + "\n" +
                        "banlist,封禁列表 - 列出封禁玩家\n" +
                        "bind,绑定 验证码 - 绑定账号\n" +
                        "bt,挖掘排行榜 [页码] - 列出挖掘排行榜\n" +
                        "channel,频道 - 获取频道邀请\n" +
                        "help,帮助 - 查看帮助\n" +
                        "info,状态 - 查看服务器信息\n" +
                        "list,在线 - 列出在线玩家\n" +
                        "ot,在线排行榜 [页码] - 列出在线排行榜\n" +
                        "rp,修改密码 新密码 - 修改登录密码(建议私聊机器人)\n" +
                        "whois,查 玩家名/QQ - 查询信息\n" +
                        (Config.admin.contains(qqNum) ? adminT : "") +
                        "当前版本:" + Config.ver + "\n" +
                        "Made By Encinet");
            }
            case "list", "在线" -> {
                PlayerNum.chance();
                // 添加玩家id列表
                List<String> onlinePlayers = new ArrayList<>();
                for (Player n : Bukkit.getServer().getOnlinePlayers()) {
                    onlinePlayers.add(n.getName() + (n.isAfk() ? " [AFK]" : ""));
                }
                // 字母顺序
                onlinePlayers = onlinePlayers.stream().sorted().collect(Collectors.toList());
                // 执行
                int num = onlinePlayers.size();
                if (Config.numMessage.containsKey(num)) {
                    List<String> messages = Config.numMessage.get(num);
                    rText = messages.get(random.nextInt(messages.size()));// 随机使用消息
                    for (int i = 0; i < num; i++) {
                        rText = rText.replace("{" + i + "}", onlinePlayers.get(i));
                    }
                } else {
                    rText = "当前 " + num + " 人在线\n" + String.join("\n", onlinePlayers);
                }
            }
            case "banlist", "封禁列表" -> {
                List<String> banedPlayers = new ArrayList<>();
                for (OfflinePlayer n : Bukkit.getServer().getBannedPlayers()) {
                    banedPlayers.add(n.getName());
                }
                // 字母顺序
                banedPlayers = banedPlayers.stream().sorted().collect(Collectors.toList());
                // 执行
                int num = banedPlayers.size();

                rText = "当前被封禁 " + num + " 人\n" + String.join(", ", banedPlayers);
            }
            case "reload", "重载" -> {
                if (hasPermission(qqNum)) {
                    Config.load();
                    rText = "配置文件已重载!";
                }
            }
            case "channel", "频道" ->
                    rText = "[mirai:app:{\"app\"\\:\"com.tencent.qun.pro\"\\,\"config\"\\:{\"autosize\"\\:0\\,\"ctime\"\\:1668305956\\,\"extendAutoSize\"\\:1\\,\"token\"\\:\"168c25ddaf0b973c4ce9f1b748950ace\"}\\,\"meta\"\\:{\"contact\"\\:{\"appId\"\\:\"3169\"\\,\"app_ark\"\\:null\\,\"ark_type\"\\:10\\,\"audio_ark\"\\:null\\,\"biz\"\\:\"ka\"\\,\"channelId\"\\:\"57694561639284782\"\\,\"channelType\"\\:\"0\"\\,\"desc\"\\:\"一个Minecraft服务器频道\"\\,\"feed_ark\"\\:null\\,\"from\"\\:\"1\"\\,\"guild_ark\"\\:{\"common_ark\"\\:{\"app_id\"\\:\"3169\"\\,\"biz\"\\:\"ka\"\\,\"desc\"\\:\"一个Minecraft服务器频道\"\\,\"from\"\\:\"1\"\\,\"guild_cover\"\\:\"https\\://groupprocover-76483.picgzc.qpic.cn/57694561639284782?imageView2/1/w/1068/h/498&t=1639285411002\"\\,\"guild_icon\"\\:\"https\\://groupprohead-76292.picgzc.qpic.cn/57694561639284782/100?t=1649046881610\"\\,\"guild_id\"\\:57694561639284782\\,\"guild_name\"\\:\"米客Mik 服务器\"\\,\"jump_url\"\\:\"https\\://qun.qq.com/qqweb/qunpro/share?_wv=3&_wwv=128&appChannel=share&inviteCode=1XgWql7RMay&from=246610&biz=ka\"\\,\"preview\"\\:\"https\\://groupprohead-76292.picgzc.qpic.cn/57694561639284782/100?t=1649046881610\"\\,\"tag\"\\:\"QQ频道\"\\,\"title\"\\:\"邀请你加入频道：米客Mik 服务器\"}\\,\"default_msg\"\\:\"朋友，邀请你来体验QQ频道!\"}\\,\"jumpUrl\"\\:\"https\\://qun.qq.com/qqweb/qunpro/share?_wv=3&_wwv=128&appChannel=share&inviteCode=1XgWql7RMay&from=246610&biz=ka\"\\,\"live_ark\"\\:null\\,\"meta_ark\"\\:null\\,\"preview\"\\:\"https\\://groupprohead-76292.picgzc.qpic.cn/57694561639284782/100?t=1649046881610\"\\,\"schedule_ark\"\\:null\\,\"tag\"\\:\"QQ频道\"\\,\"text_ark\"\\:null\\,\"title\"\\:\"邀请你加入频道：米客Mik 服务器\"\\,\"youle_ark\"\\:null}}\\,\"prompt\"\\:\"\\[频道邀请\\]\"\\,\"ver\"\\:\"1.0.2.8\"\\,\"view\"\\:\"contact\"}]";
            case "ot", "在线排行榜" -> {
                int page;
                try {
                    page = (str.length > 1) ? Math.max(Integer.parseInt(str[1]), 1) : 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
                rText = TopList.get(PLAY_ONE_MINUTE, new TopList() {
                    @Override
                    public String unit(int num) {
                        return Conversion.ticksToText(num);
                    }
                }, "在线排行榜", page);
                UUID uuid = MiraiMC.getBind(qqNum);
                if (uuid != null) {
                    OfflinePlayer oPlayer = Bukkit.getPlayer(uuid);
                    if (oPlayer != null) {
                        String name = Objects.requireNonNull(oPlayer.getName());
                        if (rText.contains(name)) {
                            rText = rText.replace(name, name + "[你]");
                        }
                    }
                }
            }
            case "bt", "挖掘排行榜" -> {
                int page;
                try {
                    page = (str.length > 1) ? Math.max(Integer.parseInt(str[1]), 1) : 0;
                } catch (NumberFormatException e) {
                    page = 1;
                }
                rText = TopList.get(MINE_BLOCK, new TopList() {
                    @Override
                    public String unit(int num) {
                        return String.valueOf(num);
                    }
                }, "挖掘排行榜", page);
            }
            case "chat", "聊天" -> {
                if (hasPermission(qqNum)) {
                    if (str.length > 1 && Objects.equals(str[1], "new")) {
                        ChatGPT.reload();
                        rText = "新会话启动";
                    } else {
                        ChatGPT.enable = !ChatGPT.enable;
                        rText = "成功" + (ChatGPT.enable ? "启用" : "禁用") + "聊天机器人";
                    }
                }
            }
            case "send", "发送" -> {
                if (hasPermission(qqNum)) {
                    rText = text;
                }
            }
            case "bind", "绑定" -> {
                if (str.length < 2) {
                    rText = "你还没有输入验证码";
                } else if (!Bind.code.containsKey(str[1])) {
                    rText = "无效验证码";
                } else
                    return Bind.qqGroup(str[1], qqNum);
            }
            case "whois", "查" -> rText = Whois.core(str.length < 2 ? null : str[1], qqNum);
            case "c", "执行" -> {
                if (!hasPermission(qqNum)) {
                    rText = "";// 没有权限
                } else if (str.length < 2) {
                    rText = "你还没有输入命令呢";
                } else {
                    StringBuilder c = new StringBuilder();
                    for (int i = 1; i < str.length; i++) {
                        c.append(str[i]);
                        if (i < (str.length - 1)) {
                            c.append(" ");
                        }
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.toString());
                        }
                    }.runTask(JavaPlugin.getProvidingPlugin(OceanBot.class));
                    rText = "指令发送完成";
                }
            }
            case "rp", "修改密码" -> {
                UUID uuid = MiraiMC.getBind(qqNum);
                if (uuid == null) {
                    rText = "你还没有绑定游戏账号呢";
                } else if (str.length < 2) {
                    rText = "请输入新密码";
                } else {
                    OfflinePlayer player = Bukkit.getPlayer(uuid);
                    assert player != null;
                    if (player.hasPlayedBefore()) {
                        String name = player.getName();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/authme password " + name + " " + str[1]);
                    } else {
                        rText = "你还没有进入服务器";
                    }
                }
            }
            case "info", "状态" -> {
                StringBuilder sb = new StringBuilder();
                sb.append("服务器版本: ").append(Bukkit.getVersion())
                        .append(String.format(" 在线玩家: %d/%d", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()))
                        .append("\n");

                long Max = Runtime.getRuntime().maxMemory();
                long Use = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                sb.append("内存: ").append(String.format("%.2f%%", Use / (double) Max * 100)).append(" (")
                        .append(unitByte(Max)).append("-").append(unitByte(Use)).append("=").append(unitByte(Max - Use))
                        .append(" 分配:").append(unitByte(Runtime.getRuntime().totalMemory())).append(")").append("\n");

                DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
                List<String> tps = new ArrayList<>(4);// tps值有4个
                for (double single : Bukkit.getTPS()) {
                    tps.add(df.format(single));
                }
                double mspt = Double.parseDouble(df.format(Bukkit.getTickTimes()[0] / 1000000));
                sb.append("TPS: ").append(tps).append(" MSPT: ").append(mspt).append("\n");

                final long dt = Bukkit.getServer().getWorldContainer().getTotalSpace();
                final long du = Bukkit.getServer().getWorldContainer().getUsableSpace();
                final long duse = dt - du;
                sb.append("线程数: ").append(Thread.currentThread().getThreadGroup().activeCount()).append(" 磁盘: ")
                        .append(unitByte(dt)).append("-").append(unitByte(duse)).append("=").append(unitByte(du));

                rText = sb.toString();
            }
            default -> rText = "";// 错误的命令
        }
        return rText;
    }

    public static String unitByte(long enter) {
        if (enter < 0)
            return "NaN";
        final double standard = 1024D;
        final DecimalFormat df = new DecimalFormat("#.00");
        if (enter <= standard) {
            return df.format(enter) + "B";
        }
        final double m = standard * standard;
        if (enter <= m) {
            return df.format(enter / standard) + "KB";
        }
        final double g = m * standard;
        if (enter <= g) {
            return df.format(enter / m) + "MB";
        }
        return df.format(enter / g) + "GB";
    }

    public static boolean hasPermission(long qqNum) {
        for (Long qq : admin) {
            if (qq.equals(qqNum)) {
                return true;
            }
        }
        return false;
    }
}
