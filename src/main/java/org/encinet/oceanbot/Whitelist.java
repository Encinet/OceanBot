package org.encinet.oceanbot;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Whitelist {
    // UUID: QQ
    private static File whitelist;

    public static void load() {
        whitelist = new File(OceanBot.plugin.getDataFolder(), "whitelist.yml");
        if (!whitelist.exists()) {
            try {
                whitelist.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void write(UUID uuid, long qq) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(whitelist);
//
//        List<String> uuids = yaml.getStringList("uuid");
//        try {
//            uuids.add(String.valueOf(uuid));
//            yaml.set("uuid", uuids);
//            yaml.save(whitelist);
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
    }

    public static long getBind(UUID uuid) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(whitelist);
        return yaml.getLong(uuid.toString(), 0);
    }

    public static UUID getBind(long qq) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(whitelist);
        Map<String, Object> values = yaml.getValues(false);
        return null;
    }
    public static void remove(long qq) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(whitelist);
    }
}

class uuid {
    private final long qq;

    public uuid(long qq) {
        this.qq = qq;
    }

    public long getQQ() {
        return qq;
    }
}

class qq {
    private final UUID uuid;

    public qq(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}