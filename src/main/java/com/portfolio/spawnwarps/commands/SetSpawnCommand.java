package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {

    private final SpawnWarps plugin;

    public SetSpawnCommand(SpawnWarps plugin) {
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
        if (!player.hasPermission("spawnwarps.setspawn")) {
            plugin.getMessageUtil().send(player, "no-permission");
            return true;
        }

        // Define spawn na localização atual
        plugin.getLocationUtil().setSpawn(player.getLocation());
        plugin.getMessageUtil().send(player, "spawn-set");

        return true;
    }
}
