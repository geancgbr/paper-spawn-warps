package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private final SpawnWarps plugin;

    public WarpCommand(SpawnWarps plugin) {
        this.plugin = plugin;
        // Registra tab completer
        plugin.getCommand("warp").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                            @NotNull String label, @NotNull String[] args) {

        // Verifica se é player
        if (!(sender instanceof Player player)) {
            plugin.getMessageUtil().send(sender, "player-only");
            return true;
        }

        // Verifica argumentos
        if (args.length < 1) {
            plugin.getMessageUtil().send(player, "usage-warp");
            return true;
        }

        String warpName = args[0];

        // Verifica se warp existe
        if (!plugin.getLocationUtil().warpExists(warpName)) {
            plugin.getMessageUtil().send(player, "warp-not-found", "warp", warpName);
            return true;
        }

        // Verifica cooldown
        if (!player.hasPermission("spawnwarps.bypass.cooldown")
                && plugin.getLocationUtil().hasCooldown(player.getUniqueId())) {
            int remaining = plugin.getLocationUtil().getRemainingCooldown(player.getUniqueId());
            plugin.getMessageUtil().send(player, "cooldown", "time", String.valueOf(remaining));
            return true;
        }

        // Teleporta
        Location warp = plugin.getLocationUtil().getWarp(warpName);
        player.teleportAsync(warp).thenAccept(success -> {
            if (success) {
                plugin.getMessageUtil().send(player, "warp-teleport", "warp", warpName);
                plugin.getLocationUtil().setCooldown(player.getUniqueId());
            }
        });

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            return plugin.getLocationUtil().getWarpNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .toList();
        }
        return new ArrayList<>();
    }
}
