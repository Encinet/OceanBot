package org.encinet.oceanbot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    static final Plugin plugin = JavaPlugin.getProvidingPlugin(OceanBot.class);

    public static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static int ver;
    public static List<String> prefix;
    public static Long BotID;
    public static List<Long> GroupID;
    public static Long MainGroup;

    public static Map<Integer, List<String>> numMessage;
    public static String noWhiteKick;
    public static List<String> chatPrefix;
    public static String serverToQQ;
    public static String join;

    public static List<Long> admin;
    
    public static boolean recallEnable;
    public static int recallMuteValue;
    public static int recallMuteTime;
    public static List<String> recallText;
    
    public static void load() {
        plugin.reloadConfig();

        ver = getConfig().getInt("ver", 3);
        prefix = getConfig().getStringList("prefix");
        BotID = getConfig().getLong("BotID");
        GroupID = getConfig().getLongList("GroupID");
        MainGroup = getConfig().getLong("MainGroup");

        numMessage = new HashMap<>();
        @NotNull List<Integer> nums = getConfig().getIntegerList("NumMessage");
        System.out.println(nums);
        for (int num : nums) {
            numMessage.put(num, getConfig().getStringList("NumMessage." + num));
            System.out.println(num + " " + numMessage.get(num));
        }

        noWhiteKick = getConfig().getString("noWhiteKick");
        join = getConfig().getString("join");
        chatPrefix = getConfig().getStringList("chat.prefix");
        serverToQQ = getConfig().getString("chat.format.server-to-qq");

        admin = getConfig().getLongList("admin");
        
        recallEnable = getConfig().getBoolean("recall.enable", true);
        recallMuteValue = getConfig().getInt("recall.mute.value", 3);
        recallMuteTime = getConfig().getInt("recall.mute.time", 120);
        recallText = getConfig().getStringList("recall.text");
    }
}
