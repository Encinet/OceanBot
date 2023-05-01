package org.encinet.oceanbot.common;

import net.kyori.adventure.text.TextComponent;

import java.util.List;
import java.util.UUID;
import org.encinet.oceanbot.common.occommand.commands.banlist;

public abstract class Adapter {
    
    public static enum PlayerStatus { None, Banned, Offine, Online }
    
    public static class Player {
        public final String name;
        public final UUID uuid;
        public PlayerStatus status;
        
        public boolean AFK;
        public int ping;
        
        public long firstPlayed;
        public long lastSeen;
        
        public Player(String name, UUID uuid, PlayerStatus status, boolean AFK, int ping, long firstPlayed, long lastSeen) {
            this.name = name;
            this.uuid = uuid;
            this.status = status;
            this.AFK = AFK;
            this.ping = ping;
            this.firstPlayed = firstPlayed;
            this.lastSeen = lastSeen;
        }
        public Player(String name, UUID uuid, PlayerStatus status) {
            this.name = name;
            this.uuid = uuid;
            this.status = status;
            this.AFK = false;
            this.ping = 0;
            this.firstPlayed = 0L;
            this.lastSeen = 0L;
        }
        
        public final boolean hasPlayedBefore() {
            return !(status == PlayerStatus.None);
        }
        public final boolean isBanned() {
            return status == PlayerStatus.Banned;
        }
        public final boolean isOnline() {
            return status == PlayerStatus.Online;
        }
    }
    
    public Server Server;
    
    public static abstract class Server {
        public abstract Player getPlayer(UUID uuid);
        public abstract Player getPlayer(String name);
        
        public abstract List<Player> getOnlinePlayers();
        public abstract List<Player> getBannedPlayers();
        
        public abstract void sendMessage(String message);
        public abstract void sendMessage(TextComponent message);
    }
}