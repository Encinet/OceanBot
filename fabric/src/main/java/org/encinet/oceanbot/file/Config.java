package org.encinet.kitebot.file;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Config {
    private static final String CONFIG_FILE_NAME = "kitebot.json";
    private static final Gson GSON = new Gson();
    private JsonObject config;
    
    public List<String> commandPrefix;
    public Long BotID;
    public String BotPassword;
    public MiraiProtocol BotProtocol;
    public String BotNick;
    
    public List<Long> EnableGroup;
    public Long MainGroup;
    public Long LogGroup;

    public Map<Integer, List<String>> numMessage;
    public String noWhiteKick;
    public List<String> chatPrefix;
    public String serverToQQ;
    public String join;

    public List<Long> admin;
    
    public boolean recallEnable;
    public int recallMuteValue;
    public int recallMuteTime;
    public List<String> recallText;
    public boolean ChatGPT_Enable;
    public List<String> ChatGPT_Tokens;
    
    public Config(File dir) throws IOException {
        File configFile = new File(dir, CONFIG_FILE_NAME);
        if (!configFile.getParentFile().isDirectory()) configFile.getParentFile().mkdirs();
        if (!configFile.exists()) {
            Files.copy(Objects.requireNonNull(
                    this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME),
                    "Couldn't find the configuration file in the JAR"), configFile.toPath());
        }
        try (FileReader reader = new FileReader(configFile)) {
            config = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw e;
        }
        load();
    }
    
    public void load() {
        commandPrefix = getAsList("prefix", String.class);
        BotID = get("bot.id", Long.class);
        BotPassword = get("bot.password", String.class);
        BotProtocol = get("bot.protocol", MiraiProtocol.class);
        BotNick = get("BotNick", String.class);
        
        EnableGroup = getAsList("group.enable", Long.class);
        MainGroup = getAsList("group.main", Long.class);
        LogGroup = getAsList("group.log", Long.class);
        
        numMessage = new HashMap<>();
        List<Map<?, ?>> nums = getAsList("NumMessage", Map.class);
        for (final Map<?, ?> map : nums) {
            int num = (int) map.get("num");
            List<String> messages = (List<String>) map.get("text");

            numMessage.put(num, messages);
        }
        
        noWhiteKick = get("noWhiteKick", String.class);
        join = get("join", String.class);
        chatPrefix = getAsList("chat.prefix", String.class);
        serverToQQ = get("chat.format.server-to-qq", String.class);
        
        admin = getAsList("admin", Long.class);
        admin.add(0L);
        
        recallEnable = get("recall.enable", Boolean.class);
        recallMuteValue = get("recall.mute.value", Integer.class);
        recallMuteTime = get("recall.mute.time", Integer.class);
        recallText = getAsList("recall.text", String.class);
        
        ChatGPT_Enable = get("ChatGPT.enable", Boolean.class);
        ChatGPT_Tokens = getAsList("ChatGPT.tokens", String.class);
    }

    private JsonElement pathToElement(String path) {
        String[] elem = path.split("\\.");
        JsonObject jsonObject = null;
        JsonElement jsonElement = null;
        for (String now : elem) {
            if (jsonObject == null) {
                jsonElement = config.get(now);
            } else {
                jsonElement = jsonObject.get(now);
            }
            if (jsonElement.isJsonObject()) {
                jsonObject = jsonElement.getAsJsonObject();
            } else {
                break;
            }
        }
        return jsonElement;
    }
            
    private <T> T get(String path, Class<T> classOfT) {
        return GSON.fromJson(pathToElement(jsonElement), classOfT);
    }
    private <T> List<T> getAsList(String path, Class<T> classOfType) {
        return GSON.fromJson(pathToElement(jsonElement), new TypeToken<List<classOfType>>(){}.getType());
    }
    private <T> List<T> getAsMap(String path, Class<T> classOfKey, Class<T> classOfValue) {
        return GSON.fromJson(pathToElement(jsonElement), new TypeToken<Map<classOfKey, classOfValue>>(){}.getType());
    }
}