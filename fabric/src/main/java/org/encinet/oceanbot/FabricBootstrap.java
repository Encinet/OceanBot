package org.encinet.oceanbot;

import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import org.encinet.oceanbot.OceanBot;

import java.util.logging.Logger;

import java.io.*;
import java.util.*;

public class FabricBootstrap implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = Logger.getLogger("OceanBot");
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
