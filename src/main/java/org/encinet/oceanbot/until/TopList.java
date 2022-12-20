package org.encinet.oceanbot.until;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public abstract class TopList {
    // 每个page多少行
    static int line = 10;

    public static String get(Statistic statistic, TopList tl, String topText, int page) {
        final int start = (line * (page - 1)) + 1;
        final int end = line * page;
        OfflinePlayer[] oPlayers = Bukkit.getOfflinePlayers();
        TreeMap<Integer, String> online = new TreeMap<>(Comparator.reverseOrder());// 倒序Treemap
        for (OfflinePlayer oPlayer : oPlayers) {
            online.put(oPlayer.getStatistic(statistic), oPlayer.getName());
        }

        StringBuilder sb = new StringBuilder();
        int size = online.size();
        int maxPage = pages(size);
        sb.append(topText).append(" ").append(page).append("/").append(maxPage).append("\n");
        if (page > maxPage) return sb.append("无").toString();

        int num = 1;
        for (Map.Entry<Integer, String> entry : online.entrySet()) {
            if (start <= num && num <= end) {
                sb.append(num).append(".").append(entry.getValue()).append(" - ").append(tl.unit(entry.getKey())).append("\n");
            } else if (num > end) break;
            num++;
        }
        return sb.toString();
    }

    public abstract String unit(int num);

    private static int pages(int length) {
        int division = length / line;
        return length % line > 0 ? division + 1 : division;
    }
}
