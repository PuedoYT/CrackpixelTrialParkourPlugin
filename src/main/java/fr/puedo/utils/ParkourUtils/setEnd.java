package fr.puedo.utils.ParkourUtils;

import fr.puedo.utils.Parkour;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class setEnd {

    private Parkour main;
    public setEnd(Parkour main, Player p, String ParkourName){
        this.main = main;
        FileConfiguration config = main.getConfig();
        config.set("parkour." + ParkourName + ".end.x", p.getLocation().getBlockX());
        config.set("parkour." + ParkourName + ".end.y", p.getLocation().getBlockY());
        config.set("parkour." + ParkourName + ".end.z", p.getLocation().getBlockZ());
        main.saveConfig();
    }


}
