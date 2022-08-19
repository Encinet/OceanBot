package org.encinet.oceanbot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.encinet.oceanbot.QQ.Group;
import org.encinet.oceanbot.event.PlayerLogin;
import org.encinet.oceanbot.event.PlayerMessage;
import org.encinet.oceanbot.event.PlayerNumber;

import java.util.Objects;
import java.util.logging.Logger;

public final class OceanBot extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("OceanBot");

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("加载配置文件...");
        saveDefaultConfig();
        reloadConfig();
        Config.load();

        logger.info("注册监听...");
        Bukkit.getPluginManager().registerEvents(new Group(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMessage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerNumber(), this);

        logger.info("注册Minecraft指令...");
        if (Bukkit.getPluginCommand("oc") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("oc")).setExecutor(new MCCommand());
        }

        logger.info("插件成功开启");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
