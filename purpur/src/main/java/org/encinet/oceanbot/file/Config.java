package org.encinet.oceanbot.file;

import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;

import org.bukkit.configuration.file.FileConfiguration;
import org.encinet.oceanbot.OceanBot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    public FileConfiguration config() {
        return OceanBot.plugin.getConfig();
    }
    
    public void load() {
        OceanBot.plugin.reloadConfig();

        ver = config().getInt("ver", 3);
        commandPrefix = config().getStringList("prefix");
        BotID = config().getLong("bot.id");
        BotPassword = config().getString("bot.password", "");
        BotProtocol = switch (config().getString("bot.protocol", "").toLowerCase()) {
            case "android_pad" -> MiraiProtocol.ANDROID_PAD;
            case "android_watch" -> MiraiProtocol.ANDROID_WATCH;
            case "ipad" -> MiraiProtocol.IPAD;
            case "macos" -> MiraiProtocol.MACOS;
            default -> MiraiProtocol.ANDROID_PHONE;
        };
        BotNick = config().getString("BotNick", "Bot");
        
        EnableGroup = config().getLongList("group.enable");
        MainGroup = config().getLong("group.main");
        LogGroup = config().getLong("group.log");

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
        // 0 is admin, Minecraft Console is 0
        admin.add(0L);

        recallEnable = config().getBoolean("recall.enable", false);
        recallMuteValue = config().getInt("recall.mute.value", 3);
        recallMuteTime = config().getInt("recall.mute.time", 120);
        recallText = config().getStringList("recall.text");

        ChatGPT_Enable = config().getBoolean("ChatGPT.enable", false);
        ChatGPT_Tokens = config().getStringList("ChatGPT.tokens");
    }
}
