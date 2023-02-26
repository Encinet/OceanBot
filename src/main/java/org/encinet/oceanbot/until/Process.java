package org.encinet.oceanbot.until;

import org.bukkit.Bukkit;
import org.encinet.oceanbot.file.Whitelist;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

public class Process {
    static final double tickSecond = 20;
    static final double second = 60;
    static final double minute = 60;
    static final double hour = 24;
    static final double day = 24;
    static DecimalFormat df = new DecimalFormat("0.01");

    public static String ticksToText(int ticks) {
        if (ticks <= (second * tickSecond)) {
            return df.format(ticks / tickSecond) + "秒";
        } else if (ticks <= (second * minute * tickSecond))  {
            return df.format(ticks / tickSecond / second) + "分";
        } else if (ticks <= (second * minute * hour * tickSecond))  {
            return df.format(ticks / tickSecond / second / minute) + "时";
        } else {
            return df.format(ticks / tickSecond / second / minute / day) + "天";
        }
    }


    /**
     * 安全地在Map每次添加1, 默认值为1
     * @param map map表
     * @param num 键
     * @param <E> 键的类型
     */
    public static <E> void mapCountAdd(Map<E, Integer> map, E num) {
        if (map.containsKey(num)) {
            int now = map.get(num) + 1;
            map.put(num, now);
        } else {
            map.put(num, 1);
        }
    }

    /**
     * 安全地从Map中获取Integer, 默认值为1
     * @param map map表
     * @param num 键
     * @return 值
     * @param <E> 键的类型
     */
    public static <E> Integer mapCountGet(Map<E, Integer> map, E num) {
        if (map.containsKey(num)) {
            int now = map.get(num) + 1;
            map.put(num, now);
            return now;
        } else {
            map.put(num, 1);
            return 1;
        }
    }

    /**
     * 从文本中解析QQ号码
     * @param text 文本
     * @return QQ号码
     */
    public static long stringToQBind(String text) {
        try {
            if (text.startsWith("@")) {
                // @123
                long num = Long.parseLong(text.substring(1));
                return Whitelist.getBind(num) == null ? 0 : num;
            } else if (Whitelist.getBind(Long.parseLong(text)) != null) {
                // 123
                return Long.parseLong(text);
            } else if (text.startsWith("[mirai:at:") && text.endsWith("]")) {
                // [mirai:at:123]
                long num = Long.parseLong(text.substring(9, text.length() - 1));
                return Whitelist.getBind(num) == null ? 0 : num;
            } else {
                return 0;
            }
        } catch (NumberFormatException e) {
            // 尝试为游戏ID
            UUID uuid = Bukkit.getOfflinePlayer(text).getUniqueId();
            return Whitelist.getBind(uuid);
        }
    }
}
