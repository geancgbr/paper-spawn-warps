package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetWarpCommand implements CommandExecutor {

    private final SpawnWarps plugin;

    public SetWarpCommand(SpawnWarps plugin) {
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

        // Verifica permissão
        if (!player.hasPermission("spawnwarps.setwarp")) {
            plugin.getMessageUtil().send(player, "no-permission");
            return true;
        }

        // Verifica argumentos
        if (args.length < 1) {
            plugin.getMessageUtil().send(player, "usage-setwarp");
            return true;
        }

        String warpName = args[0];

        // Verifica se já existe
        if (plugin.getLocationUtil().warpExists(warpName)) {
            plugin.getMessageUtil().send(player, "warp-exists");
            return true;
        }

        // Cria warp
        plugin.getLocationUtil().setWarp(warpName, player.getLocation());
        plugin.getMessageUtil().send(player, "warp-created", "warp", warpName);

        return true;
    }
}
