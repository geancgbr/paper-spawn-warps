package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DelWarpCommand implements CommandExecutor, TabCompleter {

    private final SpawnWarps plugin;

    public DelWarpCommand(SpawnWarps plugin) {
        this.plugin = plugin;
        plugin.getCommand("delwarp").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                            @NotNull String label, @NotNull String[] args) {

        // Verifica se é player
        if (!(sender instanceof Player player)) {
            plugin.getMessageUtil().send(sender, "player-only");
            return true;
        }

        // Verifica permissão
        if (!player.hasPermission("spawnwarps.delwarp")) {
            plugin.getMessageUtil().send(player, "no-permission");
            return true;
        }

        // Verifica argumentos
        if (args.length < 1) {
            plugin.getMessageUtil().send(player, "usage-delwarp");
            return true;
        }

        String warpName = args[0];

        // Verifica se existe
        if (!plugin.getLocationUtil().warpExists(warpName)) {
            plugin.getMessageUtil().send(player, "warp-not-found", "warp", warpName);
            return true;
        }

        // Deleta warp
        plugin.getLocationUtil().deleteWarp(warpName);
        plugin.getMessageUtil().send(player, "warp-deleted", "warp", warpName);

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
