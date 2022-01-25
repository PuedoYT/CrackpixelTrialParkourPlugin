package fr.puedo.utils.ParkourUtils;

import fr.puedo.utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class teleport {

    private Main main;
    public teleport(Main main, Player p, String ParkourName){
        this.main = main;
        FileConfiguration config = main.getConfig();
        if(main.getConfig().contains("parkour." + ParkourName)) {
            p.teleport(new Location(
                    Bukkit.getWorld("world"),
                    config.getDouble("parkour." + ParkourName + ".start.x"),
                    config.getDouble("parkour." + ParkourName + ".start.y"),
                    config.getDouble("parkour." + ParkourName + ".start.z")
            ));
            p.sendMessage(config.getString("TeleportMessage") + ParkourName);
        } else {
            p.sendMessage("§cError: this parkour does not exist!");
        }}

}
