package fr.puedo.utils.ParkourUtils;

import fr.puedo.utils.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class setStart {

    private Main main;
    public setStart(Main main, Player p, String ParkourName){
        this.main = main;
        FileConfiguration config = main.getConfig();
        config.set("parkour." + ParkourName + ".start.x", p.getLocation().getBlockX());
        config.set("parkour." + ParkourName + ".start.y", p.getLocation().getBlockY());
        config.set("parkour." + ParkourName + ".start.z", p.getLocation().getBlockZ());
        main.saveConfig();}

}
