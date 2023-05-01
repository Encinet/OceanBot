package org.encinet.oceanbot;

import net.kyori.adventure.text.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.text.Text;

import org.encinet.oceanbot.common.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FabricAdapter extends Adapter {
    public class Server {
        public Player getPlayer(UUID uuid) {
            PlayerManager playerManager = FabricBootstrap.SERVER.getPlayerManager();
            ServerPlayerEntity player = playerManager.getPlayer(uuid);
            
            PlayerStatus status = playerManager.getUserBanList().get(player.getGameProfile()) == null ? (player == null ? PlayerStatus.Offine : PlayerStatus.Online): PlayerStatus.Banned;
            return new Player(player.getName().toString(), player.getUuid(), status, false, player.pingMilliseconds, 0L, 0L);
        }
        public Player getPlayer(String name) {
            PlayerManager playerManager = FabricBootstrap.SERVER.getPlayerManager();
            ServerPlayerEntity player = playerManager.getPlayer(name);
            
            PlayerStatus status = playerManager.getUserBanList().get(player.getGameProfile()) == null ? (player == null ? PlayerStatus.Offine : PlayerStatus.Online): PlayerStatus.Banned;
            return new Player(player.getName().toString(), player.getUuid(), status, false, player.pingMilliseconds, 0L, 0L);
        }
        
        public List<Player> getOnlinePlayers() {
            PlayerManager playerManager = FabricBootstrap.SERVER.getPlayerManager();
            List<Player> list = new ArrayList<>();
            for (ServerPlayerEntity player : playerManager.getPlayerList()) {
                list.add(new Player(player.getName().toString(), player.getUuid(), PlayerStatus.Online));
            }
            return list;
        }
        public List<Player> getBannedPlayers() {
            PlayerManager playerManager = FabricBootstrap.SERVER.getPlayerManager();
            List<Player> list = new ArrayList<>();
            for (String playerName : playerManager.getUserBanList().getNames()) {
                UUID uuid = playerManager.getPlayer(playerName).getUuid();
                list.add(new Player(playerName, uuid, PlayerStatus.Banned));
            }
            return list;
        }
        
        public void sendMessage(String message) {
            FabricBootstrap.SERVER.sendMessage(Text.of(message));
        }
        public void sendMessage(TextComponent message) {
            FabricBootstrap.SERVER.sendMessage(message);
        }
    }
}
