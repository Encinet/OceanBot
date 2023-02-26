package org.encinet.oceanbot.QQ;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;

public class Core {
    static Bot bot;

    public Core(long qq, String password) {
        bot = BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            // 配置
            fileBasedDeviceInfo("mirai/" + qq + ".json");
            if (new File("mirai/" + qq + ".json").exists()) {
                loadDeviceInfoJson("mirai/" + qq + ".json");
            }

            // 登录协议
            setProtocol(MiraiProtocol.ANDROID_PHONE);
            // 切换心跳策略
            // https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%88%87%E6%8D%A2%E5%BF%83%E8%B7%B3%E7%AD%96%E7%95%A5
            // setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.REGISTER);
            // 缓存目录
            setCacheDir(new File("mirai/cache"));
            // 列表缓存
            enableContactCache();
        }});
        bot.login();
    }

    public Bot getBot() {
        return bot;
    }
}
