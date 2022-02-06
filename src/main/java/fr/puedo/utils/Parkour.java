package fr.puedo.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Parkour extends JavaPlugin {

    HashMap<UUID, Long> inParkourPlayers = new HashMap<>();

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

    public String isPlayerInParkour(Player player){
        if(inParkourPlayers.containsKey(player.getUniqueId())){
            return "Player [" + player.getName() + "] is in a parkour!";
        } else { return "Player [" + player.getName() + "] is not in a parkour."; }
    }
}
