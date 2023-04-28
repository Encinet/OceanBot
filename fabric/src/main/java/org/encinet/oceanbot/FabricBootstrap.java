package org.encinet.kitebot;

import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import org.encinet.kitebot.common.command.OcCommand;
import org.encinet.kitebot.event.*;
import org.encinet.kitebot.file.*;
import org.encinet.kitebot.mirai.Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class FabricBootstrap implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
    public static MinecraftServer SERVER = null;
    public static FabricServerAudiences adventure;
    
    public static Config config;
    public static Whitelist whitelist;
    
    public static Core core;
    
    // 机器人命令
    public static OcCommand occommand;
    
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
        
        ServerLoginCallback.EVENT.register((handler, server, connection, userName) -> {
            // 调用 intercept 方法来处理登录请求
            LoginInterceptor.intercept(handler, server, connection, userName);
        });
        ServerChatCallback.EVENT.register((player, message) -> {
            // 调用 listen 方法来处理聊天事件
            ChatListener.listen(player, message);
            return true;
        });
        
        try {
            FileManager fileManager = new FileManager(new File(FabricLoader.getInstance().getConfigDirectory(), "kitebot"));
            config = fileManager.config();
            whitelist = fileManager.whitelist();
	        LOGGER.info("File loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        LOGGER.info("Registering Ocean Command");
        occommand = new OcCommand();
        
		LOGGER.info("KiteBot Beta Loaded");
	}
    
    private void onServerStarting(MinecraftServer server) {
        SERVER = server;
        this.platform = FabricServerAudiences.of(server);
    }
    
    private void onServerStarted(MinecraftServer server) {
        // mirai
        new Thread(() -> {
            core = new Core(config.BotID, config.BotPassword);
            LOGGER.info("Mirai Started");
        }, "KiteBot-Mirai").start();
    }
    
    private void onServerStopping(MinecraftServer server) {
        this.platform = null;
        LOGGER.info("GoodBye");
    }

    private void onServerStopped(MinecraftServer server) {
    }
}
