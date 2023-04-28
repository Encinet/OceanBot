package org.encinet.oceanbot;

import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

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
    
    public static OceanBot oceanbot;
    
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
        
        oceanbot = new OceanBot(LOGGER, new File(FabricLoader.getInstance().getConfigDir().toFile(), "kitebot"), new FabricAdapter());
        
		LOGGER.info("KiteBot Beta Loaded");
	}
    
    private void onServerStarting(MinecraftServer server) {
        SERVER = server;
    }
    
    private void onServerStarted(MinecraftServer server) {
    }
    
    private void onServerStopping(MinecraftServer server) {
        LOGGER.info("GoodBye");
    }

    private void onServerStopped(MinecraftServer server) {
    }
}
