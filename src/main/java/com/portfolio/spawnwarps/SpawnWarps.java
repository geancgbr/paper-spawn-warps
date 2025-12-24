package com.portfolio.spawnwarps;

import com.portfolio.spawnwarps.commands.*;
import com.portfolio.spawnwarps.listeners.PlayerListener;
import com.portfolio.spawnwarps.utils.LocationUtil;
import com.portfolio.spawnwarps.utils.MessageUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnWarps extends JavaPlugin {

    private static SpawnWarps instance;
    private LocationUtil locationUtil;
    private MessageUtil messageUtil;

    @Override
    public void onEnable() {
        instance = this;
        
        // Salva config padrão se não existir
        saveDefaultConfig();
        
        // Inicializa utils
        this.messageUtil = new MessageUtil(this);
        this.locationUtil = new LocationUtil(this);
        
        // Registra comandos
        registerCommands();
        
        // Registra listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getLogger().info("SpawnWarps ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("SpawnWarps desativado!");
    }

    private void registerCommands() {
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));
    }

    // Getters
    public static SpawnWarps getInstance() {
        return instance;
    }

    public LocationUtil getLocationUtil() {
        return locationUtil;
    }

    public MessageUtil getMessageUtil() {
        return messageUtil;
    }
}
