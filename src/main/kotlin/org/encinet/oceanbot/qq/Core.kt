package org.encinet.oceanbot.qq

import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol
import java.io.File

object Core {
    fun load(qq: Long, password: String) {
        val bot = BotFactory.newBot(qq, password) {
            // 配置
            val deviceInfo = "mirai/$qq.json";
            fileBasedDeviceInfo(deviceInfo)
            if (File(deviceInfo).exists()) {
                loadDeviceInfoJson(deviceInfo)
            }
            // 登录协议
            protocol = MiraiProtocol.ANDROID_PHONE
            // 切换心跳策略
            // https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%88%87%E6%8D%A2%E5%BF%83%E8%B7%B3%E7%AD%96%E7%95%A5
            // setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.REGISTER);
            // 运行目录
            workingDir = File("mirai")
            // 缓存目录
            cacheDir = File("mirai/cache")
            // 列表缓存
            enableContactCache()
            // 重定向日志
            redirectBotLogToFile(File("mirai/bot.log"))
            redirectNetworkLogToFile(File("mirai/network.log"))
        }
    }
}