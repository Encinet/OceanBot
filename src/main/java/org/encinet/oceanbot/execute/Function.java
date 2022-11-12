package org.encinet.oceanbot.execute;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.QQ.Bind;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.encinet.oceanbot.Config.admin;

public class Function {
    private static final Random random = new Random();
    public static String on(String text, Long qqNum) {
        String rText = "";
        String[] str = text.substring(1).split(" ");
        switch (str[0]) {// 截取首位字符以后的东西
            case "help" -> {
                String adminT = "c - 执行命令(仅管理可用)\n" +
                        "reload - 重载配置 (仅管理可用)\n";
                rText = ("消息前加#可发送到服务器或QQ群\n" +
                        "当前可用指令前缀 " + Arrays.toString(Config.prefix.toArray()) + "\n" +
                        "banlist - 列出封禁玩家\n" +
                        "bind 验证码 - 绑定账号\n" +
                        "help - 查看帮助\n" +
                        "info - 查看服务器信息\n" +
                        "list - 列出在线玩家\n" +
                        "whois 玩家名/QQ - 查询信息\n" +
                        (Config.admin.contains(qqNum) ? adminT : "") +
                        "当前版本:" + Config.ver + "\n" +
                        "Made By Encinet");
            }
            case "list" -> {
                // 添加玩家id列表
                List<String> onlinePlayers = new ArrayList<>();
                for (Player n : Bukkit.getServer().getOnlinePlayers()) {
                    onlinePlayers.add(n.getName());
                }
                // 字母顺序
                onlinePlayers = onlinePlayers.stream().sorted().collect(Collectors.toList());
                // 执行
                int num = onlinePlayers.size();
                System.out.println(Config.numMessage);// test
                if (Config.numMessage.containsKey(num)) {
                    List<String> messages = Config.numMessage.get(num);
                    rText = messages.get(random.nextInt(messages.size()));// 随机使用消息
                    for (int i = 0; i < num; i++) {
                        rText.replace("{" + i + "}", onlinePlayers.get(i));
                    }
                } else {
                    rText = "当前 " + num + " 人在线\n" + String.join(", ", onlinePlayers);
                }
            }
            case "banlist" -> {
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
            case "reload" -> {
                if (hasPermission(qqNum)) {
                    Config.load();
                    rText = "配置文件已重载!";
                }
            }
            case "bind" -> {
                if (str.length < 2) {
                    rText = "你还没有输入验证码";
                } else if (!Bind.code.containsKey(str[1])) {
                    rText = "无效验证码";
                } else
                    return Bind.qqGroup(str[1], qqNum);
            }
            case "whois" -> rText = Whois.core(str.length < 2 ? null : str[1], qqNum);
            case "c" -> {
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
            case "info" -> {
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
