package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    private final SpawnWarps plugin;

    public SpawnCommand(SpawnWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, 
                            @NotNull String label, @NotNull String[] args) {
        
        // Verifica se é player
        if (!(sender instanceof Player player)) {
            plugin.getMessageUtil().send(sender, "player-only");
            return true;
        }

        // Verifica se spawn existe
        if (!plugin.getLocationUtil().hasSpawn()) {
            plugin.getMessageUtil().send(player, "spawn-not-set");
            return true;
        }

        // Verifica cooldown (ignora se tem permissão de bypass)
        if (!player.hasPermission("spawnwarps.bypass.cooldown") 
                && plugin.getLocationUtil().hasCooldown(player.getUniqueId())) {
            int remaining = plugin.getLocationUtil().getRemainingCooldown(player.getUniqueId());
            plugin.getMessageUtil().send(player, "cooldown", "time", String.valueOf(remaining));
            return true;
        }

        // Teleporta
        Location spawn = plugin.getLocationUtil().getSpawn();
        player.teleportAsync(spawn).thenAccept(success -> {
            if (success) {
                plugin.getMessageUtil().send(player, "spawn-teleport");
                plugin.getLocationUtil().setCooldown(player.getUniqueId());
            }
        });

        return true;
    }
}
