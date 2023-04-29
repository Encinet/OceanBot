package org.encinet.oceanbot.file;

import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Config {
    private File file;
    
    public List<String> commandPrefix;
    public long BotID;
    public String BotPassword;
    public MiraiProtocol BotProtocol;
    public String BotNick;
    
    public List<Long> EnableGroup;
    public long MainGroup;
    public long LogGroup;
    
    public List<String> chatPrefix;
    public String serverToQQ;
    
    public List<Long> admin;
    
    public boolean recallEnable;
    public int recallMuteValue;
    public int recallMuteTime;
    public List<String> recallText;
    
    public boolean ChatGPT_Enable;
    public List<String> ChatGPT_Tokens;
    
    public Map<Integer, List<String>> numMessage;
    public String noWhiteKick;
    public String join;
    
    public Config(File file) {
        this.file = file;
        load();
    }
    
    public void load() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(file)) {
            Map<String, Object> data = yaml.load(inputStream);
            
            commandPrefix = (List<String>) data.get("prefix");
            
            Map<String, Object> bot = (Map<String, Object>) data.get("bot");
            BotID = (long) bot.get("id");
            BotPassword = (String) bot.get("password");
            BotProtocol = switch (((String) bot.get("password")).toLowerCase()) {
                case "android_pad" -> MiraiProtocol.ANDROID_PAD;
                case "android_watch" -> MiraiProtocol.ANDROID_WATCH;
                case "ipad" -> MiraiProtocol.IPAD;
                case "macos" -> MiraiProtocol.MACOS;
                default -> MiraiProtocol.ANDROID_PHONE;
            };
            BotNick = (String) bot.get("main-group-nick");
            
            Map<String, Object> group = (Map<String, Object>) data.get("group");
            EnableGroup = (List<Long>) group.get("enable");
            MainGroup = (long) group.get("main");
            LogGroup = (long) group.get("log");
            
            Map<String, Object> chat = (Map<String, Object>) data.get("chat");
            chatPrefix = (List<String>) chat.get("prefix");
            Map<String, Object> chatFormat = (Map<String, Object>) chat.get("format");
            serverToQQ = (String) chatFormat.get("server-to-qq");
            
            admin = (List<Long>) data.get("admin");
            
            Map<String, Object> recall = (Map<String, Object>) data.get("recall");
            recallEnable = (boolean) recall.get("enable");
            Map<String, Object> mute = (Map<String, Object>) recall.get("mute");
            recallMuteValue = (int) mute.get("value");
            recallMuteTime = (int) mute.get("time");
            recallText = (List<String>) recall.get("text");
            
            Map<String, Object> chatGPT = (Map<String, Object>) data.get("ChatGPT");
            ChatGPT_Enable = (boolean) chatGPT.get("enable");
            ChatGPT_Tokens = (List<String>) chatGPT.get("tokens");
            
            numMessage = new HashMap<>();
            List<Map<?, ?>> nums = (List<Map<?, ?>>) data.get("NumMessage");
            for (final Map<?, ?> map : nums) {
                int num = (int) map.get("num");
                List<String> messages = (List<String>) map.get("text");

                numMessage.put(num, messages);
            }
            
            noWhiteKick = (String) data.get("noWhiteKick");
            join = (String) data.get("join");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}