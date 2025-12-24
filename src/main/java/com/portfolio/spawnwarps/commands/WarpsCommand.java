package com.portfolio.spawnwarps.commands;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WarpsCommand implements CommandExecutor {

    private final SpawnWarps plugin;

    public WarpsCommand(SpawnWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                            @NotNull String label, @NotNull String[] args) {

        Set<String> warps = plugin.getLocationUtil().getWarpNames();

        // Verifica se tem warps
        if (warps.isEmpty()) {
            plugin.getMessageUtil().send(sender, "warp-list-empty");
            return true;
        }

        // Lista warps
        String warpList = String.join(", ", warps);
        plugin.getMessageUtil().send(sender, "warp-list", "warps", warpList);

        return true;
    }
}
