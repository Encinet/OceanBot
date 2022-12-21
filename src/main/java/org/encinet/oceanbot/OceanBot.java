package org.encinet.oceanbot;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    public static Economy econ;
    public static boolean vaultSupportEnabled = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("加载配置文件");
        saveDefaultConfig();
        reloadConfig();
        Config.load();

        logger.info("加载依赖");
        if (getServer().getPluginManager().getPlugin("Vault") != null && getServer().getPluginManager().getPlugin("Vault").isEnabled()) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (rsp == null) {
                logger.warning("无法找到经济插件");
                vaultSupportEnabled = false;
            } else {
                econ = rsp.getProvider();
                logger.info("Vault Hook成功");
                vaultSupportEnabled = true;
            }
        } else {
            logger.warning("Vault 未启用");
        }

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
        if (Bukkit.getPluginCommand("sign") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("sign")).setExecutor(new Sign());
        }

        logger.info("插件成功开启");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
