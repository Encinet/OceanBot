package org.encinet.oceanbot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    static final Plugin plugin = JavaPlugin.getProvidingPlugin(OceanBot.class);
    
    public static FileConfiguration config() {
        return plugin.getConfig();
    }

    public static int ver;
    public static List<String> prefix;
    public static Long BotID;
    public static String BotNick;
    public static List<Long> GroupID;
    public static Long MainGroup;
    public static String ChatToken;

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

        ver = config().getInt("ver", 3);
        prefix = config().getStringList("prefix");
        BotID = config().getLong("BotID");
        BotNick = config().getString("BotNick", "Bot");
        GroupID = config().getLongList("GroupID");
        MainGroup = config().getLong("MainGroup");
        ChatToken = config().getString("ChatGPT-Token");

        numMessage = new HashMap<>();
        List<Map<?, ?>> nums = config().getMapList("NumMessage");
        for (final Map<?, ?> map : nums) {
            int num = (int) map.get("num");
            List<String> messages = (List<String>) map.get("text");

            numMessage.put(num, messages);
        }

        noWhiteKick = config().getString("noWhiteKick");
        join = config().getString("join");
        chatPrefix = config().getStringList("chat.prefix");
        serverToQQ = config().getString("chat.format.server-to-qq");

        admin = config().getLongList("admin");
        
        recallEnable = config().getBoolean("recall.enable", true);
        recallMuteValue = config().getInt("recall.mute.value", 3);
        recallMuteTime = config().getInt("recall.mute.time", 120);
        recallText = config().getStringList("recall.text");
    }
}
