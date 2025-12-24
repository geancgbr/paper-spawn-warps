package com.portfolio.spawnwarps.utils;

import com.portfolio.spawnwarps.SpawnWarps;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class MessageUtil {

    private final SpawnWarps plugin;
    private final LegacyComponentSerializer serializer;

    public MessageUtil(SpawnWarps plugin) {
        this.plugin = plugin;
        this.serializer = LegacyComponentSerializer.legacyAmpersand();
    }

    /**
     * Envia mensagem formatada com prefix
     */
    public void send(CommandSender sender, String key) {
        String prefix = plugin.getConfig().getString("messages.prefix", "");
        String message = plugin.getConfig().getString("messages." + key, key);
        sender.sendMessage(colorize(prefix + message));
    }

    /**
     * Envia mensagem com placeholders
     */
    public void send(CommandSender sender, String key, String placeholder, String value) {
        String prefix = plugin.getConfig().getString("messages.prefix", "");
        String message = plugin.getConfig().getString("messages." + key, key);
        message = message.replace("{" + placeholder + "}", value);
        sender.sendMessage(colorize(prefix + message));
    }

    /**
     * Converte códigos de cor (&) para Component
     */
    public Component colorize(String text) {
        return serializer.deserialize(text);
    }

    /**
     * Retorna mensagem raw da config
     */
    public String getRaw(String key) {
        return plugin.getConfig().getString("messages." + key, key);
    }
}
