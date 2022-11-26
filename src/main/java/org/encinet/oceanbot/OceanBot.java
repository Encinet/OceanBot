package org.encinet.oceanbot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.encinet.oceanbot.QQ.Friend;
import org.encinet.oceanbot.QQ.Group;
import org.encinet.oceanbot.event.PlayerLogin;
import org.encinet.oceanbot.event.PlayerMessage;
import org.encinet.oceanbot.event.PlayerNum;

import java.util.Objects;
import java.util.logging.Logger;

public final class OceanBot extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("OceanBot");

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("加载配置文件");
        saveDefaultConfig();
        reloadConfig();
        Config.load();

        logger.info("注册监听");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Group(), this);
        pm.registerEvents(new Friend(), this);
        pm.registerEvents(new PlayerLogin(), this);
        pm.registerEvents(new PlayerMessage(), this);
        pm.registerEvents(new PlayerNum(), this);

        logger.info("注册Minecraft指令");
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
