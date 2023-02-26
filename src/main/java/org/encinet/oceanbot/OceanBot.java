package org.encinet.oceanbot;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.encinet.oceanbot.command.MCCommand;
import org.encinet.oceanbot.command.Maintenance;
import org.encinet.oceanbot.command.Sign;
import org.encinet.oceanbot.event.PlayerLogin;
import org.encinet.oceanbot.event.PlayerMessage;
import org.encinet.oceanbot.event.PlayerNum;
import org.encinet.oceanbot.QQ.Core;
import org.encinet.oceanbot.QQ.event.Friend;
import org.encinet.oceanbot.QQ.event.Group;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class OceanBot extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("OceanBot");
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    public static Economy econ;
    public static boolean vaultSupportEnabled = false;
    public static final ClassLoader loader = OceanBot.class.getClassLoader();
    static Plugin plugin;
    public static Core core;
    public static Runnable qq;

    @Override // 加载插件
    public void onLoad() {
//        Thread.currentThread().setContextClassLoader(this.getClassLoader());
        // Service loading.
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = JavaPlugin.getProvidingPlugin(OceanBot.class);

        logger.info("加载配置文件");
        saveDefaultConfig();
        reloadConfig();
        Config.load();
        Whitelist.load();

        // mirai
        qq = () -> {
            logger.info("启动Bot中");
            ClassLoader old = Thread.currentThread().getContextClassLoader();
            try {
                System.out.println(OceanBot.loader);
                Thread.currentThread().setContextClassLoader(OceanBot.loader);
                GlobalEventChannel.INSTANCE.registerListenerHost(new Group());
                GlobalEventChannel.INSTANCE.registerListenerHost(new Friend());
            } finally {
                Thread.currentThread().setContextClassLoader(old);
            }

            core = new Core(1802732019, "5CMg66JcKSZydi");
            core.getBot().join();
        };
//        qq.run();
//        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, qq, 20);

        // 依赖
        logger.info("加载依赖");
        if (getServer().getPluginManager().getPlugin("Vault") != null && Objects.requireNonNull(getServer().getPluginManager().getPlugin("Vault")).isEnabled()) {
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
        if (Bukkit.getPluginCommand("oc") != null) {
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
}
