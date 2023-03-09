package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;

import java.util.*;
import java.text.DecimalFormat;

import org.encinet.oceanbot.common.occommand.BasicCommand;

public class info extends BasicCommand {
    public info() {
        super("info", "awa", "aaa", false);
    }
    
    @Override
    public String onCommand(String label, long qq, boolean color) {
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

                return sb.toString();
    }
    public String onTab(String[] args, long qq) {return null;}
    
    private static String unitByte(long enter) {
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
}