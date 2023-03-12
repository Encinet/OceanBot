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
import java.util.Objects;
import java.util.logging.Logger;

public final class OceanBot implements BukkitPlugin {
    public static final OceanBot INSTANCE = new OceanBot();
    public static final Logger logger = Logger.getLogger("OceanBot");
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    public static Economy econ;
    public static boolean vaultSupportEnabled = false;
    public static OcCommand occommand;
    //插件主类
    public static Plugin plugin = BukkitTemplate.getPlugin();
    public static Core core;
    public static Runnable qq;

    @Override // 加载插件
    public void onLoad() {
//        Thread.currentThread().setContextClassLoader(this.getClassLoader());
        // Service loading.
    }

    @Override
    public void onEnable() {
        logger.info("加载配置文件");
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        Config.load();
        Whitelist.load();

        // 依赖
        logger.info("加载依赖");
        if (Bukkit.getPluginManager().getPlugin("Vault") != null && Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Vault")).isEnabled()) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
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
        pm.registerEvents(new PlayerLogin(), plugin);
        pm.registerEvents(new PlayerMessage(), plugin);
        pm.registerEvents(new PlayerNum(), plugin);

        logger.info("注册Ocean指令");
        occommand = new OcCommand();
        
        logger.info("注册Minecraft指令");
        if (Bukkit.getPluginCommand("oc") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("oc")).setExecutor(new MCCommand());
        }
        if (Bukkit.getPluginCommand("sign") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("sign")).setExecutor(new Sign());
        }
        if (Bukkit.getPluginCommand("mt") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("mt")).setExecutor(new Maintenance());
        }

        logger.info("插件成功开启");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static File getFile(String fileName) {
        return new File(System.getProperty("user.dir") + "/plugins/OceanBot/" + fileName);
    }

    /**
     * sync启动
     */
    @Override
    public void onAsyncEnable() {
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(BukkitTemplate.isolatedClassLoader);
        logger.info("启动Bot中");
        try {
            core = new Core(1802732019, "5CMg66JcKSZydi");
            core.getBot().join();
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
        DisableHook.addTask(()->{
            core.getBot().close();
        });
    }
}
