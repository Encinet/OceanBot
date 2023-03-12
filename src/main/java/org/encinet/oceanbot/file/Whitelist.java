package org.encinet.oceanbot.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.encinet.oceanbot.OceanBot;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Whitelist {

    public static void load() {
    }

    public static void write(UUID uuid, String name, long qq) {
    }

    public static UUID getBindUUID(String name) {
        return null;
    }
    public static UUID getBindUUID(long qq) {
        return null;
    }
    public static String getBindName(UUID uuid) {
        return null;
    }
    public static String getBindName(long qq) {
        return null;
    }
    public static long getBindQQ(UUID uuid) {
        return 0;
    }
    public static long getBindQQ(String name) {
        return 0;
    }
    
    public static boolean contains(UUID uuid) {
        return true;
    }
    public static boolean contains(String name) {
        return true;
    }
    public static boolean contains(long qq) {
        return true;
    }
    
    public static void remove(UUID uuid) {
        if (contains(uuid)) {
            
        }
    }
    public static void remove(String name) {
        UUID uuid = getBindUUID(name);
        remove(uuid);
    }
    public static void remove(long qq) {
        UUID uuid = getBindUUID(qq);
        remove(uuid);
    }
}
