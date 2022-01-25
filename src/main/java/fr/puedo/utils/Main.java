package fr.puedo.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    List<UUID> inParkourPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("§6§lUtils §r§ahas been enabled");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("parkour").setExecutor(new ParkourHandler(this));
        getServer().getPluginManager().registerEvents(new ParkourJoinListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
