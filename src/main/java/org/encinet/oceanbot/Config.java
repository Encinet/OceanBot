package org.encinet.oceanbot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    static final Plugin config = JavaPlugin.getProvidingPlugin(OceanBot.class);

    public static FileConfiguration getConfig() {
        return config.getConfig();
    }

    public static int ver;
    public static List<String> prefix;
    public static Long BotID;
    public static Long GroupID;
    public static Boolean gnc;
    public static Map<Integer, String> numMessage;
    public static String noWhiteKick;

    public static void load() {
        ver = getConfig().getInt("ver", 3);
        prefix = getConfig().getStringList("prefix");
        BotID = getConfig().getLong("BotID");
        GroupID = getConfig().getLong("GroupID");
        gnc = getConfig().getBoolean("gnc", true);

        numMessage = new HashMap<>();
        for (int num : getConfig().getIntegerList("NumMessage")) {
            numMessage.put(num, getConfig().getString("NumMessage." + num, ""));
        }

        noWhiteKick = getConfig().getString("noWhiteKick");
    }
}
