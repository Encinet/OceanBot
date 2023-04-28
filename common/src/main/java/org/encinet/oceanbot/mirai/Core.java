package org.encinet.oceanbot.mirai;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;

import org.encinet.oceanbot.mirai.event.*;

import java.io.File;

public class Core {
    static Bot bot;

    public Core(long qq, String password, MiraiProtocol protocal) {
        // 数据文件夹
        File dataFolder = new File("mirai/" + qq + "/");
        if (!dataFolder.exists()) {
            // mkdir() 是创建文件夹不含父文件夹
            // mkdirs() 是创建文件夹含父文件夹
            dataFolder.mkdirs();
        }
        // 注册事件
        GlobalEventChannel.INSTANCE.registerListenerHost(new Friend());
        GlobalEventChannel.INSTANCE.registerListenerHost(new Group());
        GlobalEventChannel.INSTANCE.registerListenerHost(new Other());
        // 机器人创建
        bot = BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            // 配置
            fileBasedDeviceInfo(new File(dataFolder, "device.json").toString());
            // 登录协议
            setProtocol(protocal);
            // 切换心跳策略
            // https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%88%87%E6%8D%A2%E5%BF%83%E8%B7%B3%E7%AD%96%E7%95%A5
            // setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.REGISTER);
            // 缓存目录
            setCacheDir(new File(dataFolder, "cache"));
            // 列表缓存
            enableContactCache();
        }});
        bot.login();
    }

    public Bot getBot() {
        return bot;
    }
}