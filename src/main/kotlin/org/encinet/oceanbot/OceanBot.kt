package org.encinet.oceanbot

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.encinet.oceanbot.file.Config
import org.encinet.oceanbot.file.Whitelist
import org.encinet.oceanbot.qq.Core
import java.io.File
import java.util.logging.Logger

class OceanBot : JavaPlugin() {

    /**
     * 在其他线程加载，比onEnable先调用,结束了才调用onEnable
     */
    override fun onLoad() {}

    /**
     * 在插件启用后运行
     */
    override fun onEnable() {
        // Plugin startup logic
        plugin = getProvidingPlugin(OceanBot::class.java)
        Companion.logger.info("加载配置文件")
        saveDefaultConfig()
        reloadConfig()
        Config.load()
        Whitelist.load()

        // 依赖
        Companion.logger.info("加载依赖")
        if (server.pluginManager.getPlugin("Vault") != null
            && server.pluginManager.getPlugin("Vault")!!.isEnabled
        ) {
            val rsp = server.servicesManager.getRegistration<Economy>(
                Economy::class.java
            )
            if (rsp != null) {
                econ = rsp.getProvider()
            }
            Companion.logger.info("Vault Hook成功")
            vaultSupportEnabled = true
        } else {
            Companion.logger.warning("Vault 未启用")
        }

//        Companion.logger.info("注册监听")
//        val pm: PluginManager = Bukkit.getPluginManager()
//        pm.registerEvents(PlayerLogin(), this)
//        pm.registerEvents(PlayerMessage(), this)
//        pm.registerEvents(PlayerNum(), this)
//
//        Companion.logger.info("注册Minecraft指令")
//        Bukkit.getPluginCommand("oc").setExecutor(MCCommand())
//        Bukkit.getPluginCommand("sign").setExecutor(Sign())
//        Bukkit.getPluginCommand("mt").setExecutor(Maintenance())

        Core.load(1802732019, "5CMg66JcKSZydi")

        Companion.logger.info("插件成功开启")
    }

    /**
     * 在插件停用时运行
     */
    override fun onDisable() {
        // Plugin shutdown logic
        Companion.logger.info("Goodbye")
    }

    companion object {
        val logger: Logger = Logger.getLogger("OceanBot")
        const val prefix = " §6Ocean§fBot §8>> §r"
        var econ: Economy? = null
        var vaultSupportEnabled = false
        var plugin: Plugin? = null
        fun getFile(fileName: String): File {
            return File(System.getProperty("user.dir") + "/plugins/OceanBot/" + fileName)
        }
    }
}