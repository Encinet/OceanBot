package org.encinet.oceanbot;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.encinet.oceanbot.QQ.Core;
import org.encinet.oceanbot.command.MCCommand;
import org.encinet.oceanbot.command.Maintenance;
import org.encinet.oceanbot.command.Sign;
import org.encinet.oceanbot.common.occommand.OcCommand;
import org.encinet.oceanbot.event.PlayerLogin;
import org.encinet.oceanbot.event.PlayerMessage;
import org.encinet.oceanbot.event.PlayerNum;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.file.Whitelist;
import top.iseason.bukkittemplate.BukkitPlugin;
import top.iseason.bukkittemplate.BukkitTemplate;
import top.iseason.bukkittemplate.DisableHook;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class BukkitBootstrap implements BukkitPlugin {
    // 插件主类
    public static Plugin plugin = BukkitTemplate.getPlugin();
    public static final BukkitBootstrap INSTANCE = new BukkitBootstrap();
    public static final Logger logger = Logger.getLogger("OceanBot");
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    // 经济
    public static Economy econ;
    public static boolean vaultSupportEnabled = false;
    // mirai
    public static Core core;
    // 机器人命令
    public static OcCommand occommand;
    // 白名单
    public static Whitelist whitelist;

    @Override // 加载插件
    public void onLoad() {
        loadPluginFiles();
    }
    
    @Override
    public void onEnable() {
        basicLoad();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * sync启动
     */
    @Override
    public void onAsyncEnable() {
        startMirai();
    }
    
    public static void loadPluginFiles() {
        logger.info("Loading Config.yml");
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        Config.load();
        try {
            logger.info("Loading Whitelist");
            whitelist = new Whitelist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void basicLoad() {
        // 依赖
        logger.info("Loading Dependencies");
        if (Bukkit.getPluginManager().getPlugin("Vault") != null && Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Vault")).isEnabled()) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (rsp == null) {
                logger.warning("Unable To Find The Economy Plugin");
                vaultSupportEnabled = false;
            } else {
                econ = rsp.getProvider();
                logger.info("Vault Hook Successfully");
                vaultSupportEnabled = true;
            }
        } else {
            logger.warning("Vault Feature Disable");
        }

        logger.info("Registering Event Listeners");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerLogin(), plugin);
        pm.registerEvents(new PlayerMessage(), plugin);
        pm.registerEvents(new PlayerNum(), plugin);

        logger.info("Registering Ocean Command");
        occommand = new OcCommand();
        
        logger.info("Registering Minecraft Command");
        if (Bukkit.getPluginCommand("oc") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("oc")).setExecutor(new MCCommand());
        }
        if (Bukkit.getPluginCommand("sign") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("sign")).setExecutor(new Sign());
        }
        if (Bukkit.getPluginCommand("mt") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("mt")).setExecutor(new Maintenance());
        }

        logger.info("Plugin Loaded Successfully");
    }
    
    public static void startMirai() {
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(BukkitTemplate.isolatedClassLoader);
        logger.info("Starting Mirai");
        try {
            core = new Core(Config.BotID, Config.BotPassword);
            core.getBot().join();
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
        DisableHook.addTask(()->{
            core.getBot().close();
        });
    }
    
    public static File getFile(String fileName) {
        return new File(System.getProperty("user.dir") + "/plugins/OceanBot/" + fileName);
    }
}
