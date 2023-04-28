package org.encinet.oceanbot;

import org.encinet.oceanbot.common.Adapter;
import org.encinet.oceanbot.common.occommand.OcCommand;
import org.encinet.oceanbot.mirai.Core;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.file.Whitelist;

import java.sql.SQLException;
import java.io.File;
import java.util.logging.Logger;

public class OceanBot {
    public static Logger logger;
    final File dir;
    final Adapter adapter;
    // mirai
    public static Core core;
    // 机器人命令
    public static OcCommand occommand;
    // 配置
    public static Config config;
    // 白名单
    public static Whitelist whitelist;
    
    public OceanBot(Logger logger, File dir, Adapter adapter) {
        this.logger = logger;
        this.dir = dir;
        this.adapter = adapter;
    }
    
    public void load() {
        logger.info("Loading Config");
        config = new Config(new File(dir, "config.yml"));
        try {
            logger.info("Loading Whitelist");
            whitelist = new Whitelist(new File(dir, "whitelist.db").getPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Loading OcCommand");
        occommand = new OcCommand(adapter);
    }
    
    public void loadMirai() {
        core = new Core(config.BotID, config.BotPassword, config.BotProtocol);
        core.getBot().join();
    }
}
