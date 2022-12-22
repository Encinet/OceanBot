package org.encinet.oceanbot.until;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.encinet.oceanbot.Config;
import org.encinet.oceanbot.OceanBot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

public class Money {
    public static Set<Long> qq = new CopyOnWriteArraySet<>();
    private static Map<Long, Map<String, Integer>> num = new ConcurrentHashMap<>();
    // inclusive
    private static final int start = -300;
    private static final int end = 800;

    public static int get(String text) {
        Map<String, Integer> today = getTodayMap();
        if (today.containsKey(text)) {
            return today.get(text);
        } else {
            int random = random();
            today.put(text, random);
            // 屏蔽词检测
            for (String n : Config.recallText) {
                if (text.equals(n) || text.contains(n)) {
                    return random < 0 ? random : -random;
                }
            }
            return random;
        }
    }

    public static int get() {
        return random();
    }

    // 判断玩家是否有这个钱
    public static double trueMoney(OfflinePlayer player, double change) {
        double playerHave = OceanBot.econ.getBalance(player);
        // playerHave do not smaller than 0
        if (playerHave == 0) return 0;
        return playerHave > -change ? change : -playerHave;
    }

    public static Map<String, Integer> getTodayMap() {
        long epochDay = LocalDate.now().toEpochDay();
        if (num.containsKey(epochDay)) {
            return num.get(epochDay);
        } else {
            Map<String, Integer> today = new TreeMap<>(Comparator.reverseOrder());
            num.put(epochDay, today);
            return today;
        }
    }

    private static int random() {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }

    public static EconomyResponse change(OfflinePlayer player, double amount) {
        return BigDecimal.valueOf(amount).compareTo(BigDecimal.valueOf(0)) > 0 ? OceanBot.econ.depositPlayer(player, amount) : OceanBot.econ.withdrawPlayer(player, -amount);
    }
}
