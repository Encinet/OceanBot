package org.encinet.oceanbot.until;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class QQCommandSender implements ConsoleCommandSender {
    private final ConsoleCommandSender wrappedSender;
    private final Spigot spigotWrapper;
    private final StringBuilder msgLog = new StringBuilder();

    public String get() {
        return msgLog.toString();
    }
    
    private class Spigot extends CommandSender.Spigot {
        /**
         * Sends this sender a chat component.
         *
         * @param component the components to send
         */
        public void sendMessage(@NotNull net.md_5.bungee.api.chat.BaseComponent component) {
            msgLog.append(BaseComponent.toLegacyText(component)).append('\n');
            wrappedSender.spigot().sendMessage();
        }

        /**
         * Sends an array of components as a single message to the sender.
         *
         * @param components the components to send
         */
        public void sendMessage(@NotNull net.md_5.bungee.api.chat.BaseComponent... components) {
            msgLog.append(BaseComponent.toLegacyText(components)).append('\n');
            wrappedSender.spigot().sendMessage(components);
        }
    }

    public String getMessageLog() {
        return msgLog.toString();
    }

    public String getMessageLogStripColor() {
        return ChatColor.stripColor(msgLog.toString());
    }

    public void clearMessageLog() {
        msgLog.setLength(0);
    }


    public QQCommandSender(ConsoleCommandSender wrappedSender) {
        this.wrappedSender = wrappedSender;
        spigotWrapper = new Spigot();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        wrappedSender.sendMessage(message);
        msgLog.append(message).append('\n');
    }

    @Override
    public void sendMessage(@NotNull String... messages) {
        wrappedSender.sendMessage(messages);
        for (String message : messages) {
            msgLog.append(message).append('\n');
        }
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        wrappedSender.sendMessage(sender, message);
        msgLog.append(message).append('\n');
    }
    
    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        wrappedSender.sendMessage(sender, messages);
        for (String message : messages) {
            msgLog.append(message).append('\n');
        }
    }
    
    @Override
    public @NotNull Server getServer() {
        return wrappedSender.getServer();
    }

    @Override
    public @NotNull String getName() {
        // return "OrderFulfiller";
        return "OceanBot-Mirai";
    }

    // paper start
    @Override
    public @NotNull net.kyori.adventure.text.Component name() {
        return net.kyori.adventure.text.Component.text("OceanBot-Mirai");
    }
    // paper stop
    
    @Override
    public @NotNull CommandSender.Spigot spigot() {
        return spigotWrapper;
    }

    @Override
    public boolean isConversing() {
        return wrappedSender.isConversing();
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {
        wrappedSender.acceptConversationInput(input);
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return wrappedSender.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        wrappedSender.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {
        wrappedSender.abandonConversation(conversation, details);
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        msgLog.append(message).append('\n');
        wrappedSender.sendRawMessage(message);
    }
    
    @Override
    public void sendRawMessage(@Nullable UUID sender, @NotNull String message) {
        msgLog.append(message).append('\n');
        wrappedSender.sendRawMessage(sender, message);
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return wrappedSender.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return wrappedSender.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return wrappedSender.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return wrappedSender.hasPermission(perm);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return wrappedSender.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return wrappedSender.addAttachment(plugin);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return wrappedSender.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return wrappedSender.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
        wrappedSender.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        wrappedSender.recalculatePermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return wrappedSender.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return wrappedSender.isOp();
    }

    @Override
    public void setOp(boolean value) {
        wrappedSender.setOp(value);
    }
}
 
