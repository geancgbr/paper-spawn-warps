package com.portfolio.spawnwarps.listeners;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final SpawnWarps plugin;

    public PlayerListener(SpawnWarps plugin) {
        this.plugin = plugin;
    }

    /**
     * Teleporta player ao spawn quando respawna (morre)
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {
        if (!plugin.getLocationUtil().hasSpawn()) {
            return;
        }

        Location spawn = plugin.getLocationUtil().getSpawn();
        event.setRespawnLocation(spawn);
    }

    /**
     * Teleporta novos jogadores ao spawn na primeira entrada
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onFirstJoin(PlayerJoinEvent event) {
        // Só teleporta se nunca jogou antes
        if (event.getPlayer().hasPlayedBefore()) {
            return;
        }

        if (!plugin.getLocationUtil().hasSpawn()) {
            return;
        }

        Location spawn = plugin.getLocationUtil().getSpawn();
        event.getPlayer().teleportAsync(spawn);
    }
}
