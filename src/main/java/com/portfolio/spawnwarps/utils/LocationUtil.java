package com.portfolio.spawnwarps.utils;

import com.portfolio.spawnwarps.SpawnWarps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LocationUtil {

    private final SpawnWarps plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public LocationUtil(SpawnWarps plugin) {
        this.plugin = plugin;
    }

    // ==================== SPAWN ====================

    public void setSpawn(Location location) {
        plugin.getConfig().set("spawn.world", location.getWorld().getName());
        plugin.getConfig().set("spawn.x", location.getX());
        plugin.getConfig().set("spawn.y", location.getY());
        plugin.getConfig().set("spawn.z", location.getZ());
        plugin.getConfig().set("spawn.yaw", location.getYaw());
        plugin.getConfig().set("spawn.pitch", location.getPitch());
        plugin.saveConfig();
    }

    public Location getSpawn() {
        if (!plugin.getConfig().contains("spawn.world")) {
            return null;
        }

        String worldName = plugin.getConfig().getString("spawn.world");
        if (Bukkit.getWorld(worldName) == null) {
            return null;
        }

        return new Location(
            Bukkit.getWorld(worldName),
            plugin.getConfig().getDouble("spawn.x"),
            plugin.getConfig().getDouble("spawn.y"),
            plugin.getConfig().getDouble("spawn.z"),
            (float) plugin.getConfig().getDouble("spawn.yaw"),
            (float) plugin.getConfig().getDouble("spawn.pitch")
        );
    }

    public boolean hasSpawn() {
        return getSpawn() != null;
    }

    // ==================== WARPS ====================

    public void setWarp(String name, Location location) {
        String path = "warps." + name.toLowerCase();
        plugin.getConfig().set(path + ".world", location.getWorld().getName());
        plugin.getConfig().set(path + ".x", location.getX());
        plugin.getConfig().set(path + ".y", location.getY());
        plugin.getConfig().set(path + ".z", location.getZ());
        plugin.getConfig().set(path + ".yaw", location.getYaw());
        plugin.getConfig().set(path + ".pitch", location.getPitch());
        plugin.saveConfig();
    }

    public Location getWarp(String name) {
        String path = "warps." + name.toLowerCase();
        
        if (!plugin.getConfig().contains(path + ".world")) {
            return null;
        }

        String worldName = plugin.getConfig().getString(path + ".world");
        if (Bukkit.getWorld(worldName) == null) {
            return null;
        }

        return new Location(
            Bukkit.getWorld(worldName),
            plugin.getConfig().getDouble(path + ".x"),
            plugin.getConfig().getDouble(path + ".y"),
            plugin.getConfig().getDouble(path + ".z"),
            (float) plugin.getConfig().getDouble(path + ".yaw"),
            (float) plugin.getConfig().getDouble(path + ".pitch")
        );
    }

    public boolean warpExists(String name) {
        return plugin.getConfig().contains("warps." + name.toLowerCase() + ".world");
    }

    public void deleteWarp(String name) {
        plugin.getConfig().set("warps." + name.toLowerCase(), null);
        plugin.saveConfig();
    }

    public Set<String> getWarpNames() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("warps");
        if (section == null) {
            return Set.of();
        }
        return section.getKeys(false);
    }

    // ==================== COOLDOWN ====================

    public boolean hasCooldown(UUID playerId) {
        if (!cooldowns.containsKey(playerId)) {
            return false;
        }
        
        long cooldownTime = plugin.getConfig().getInt("teleport-cooldown", 3) * 1000L;
        long timePassed = System.currentTimeMillis() - cooldowns.get(playerId);
        
        return timePassed < cooldownTime;
    }

    public int getRemainingCooldown(UUID playerId) {
        if (!cooldowns.containsKey(playerId)) {
            return 0;
        }
        
        long cooldownTime = plugin.getConfig().getInt("teleport-cooldown", 3) * 1000L;
        long timePassed = System.currentTimeMillis() - cooldowns.get(playerId);
        long remaining = cooldownTime - timePassed;
        
        return (int) Math.ceil(remaining / 1000.0);
    }

    public void setCooldown(UUID playerId) {
        cooldowns.put(playerId, System.currentTimeMillis());
    }
}
