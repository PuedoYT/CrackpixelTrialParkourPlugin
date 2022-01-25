package fr.puedo.utils.ParkourUtils;

import fr.puedo.utils.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class createCheckpoint {
    private Main main;

    public createCheckpoint(Main main, String parkourName, int CPName, Player p) {
        this.main = main;
        FileConfiguration config = main.getConfig();
        config.createSection("parkour." + parkourName + ".checkpoints." + CPName + ".x");
        config.createSection("parkour." + parkourName + ".checkpoints." + CPName + ".y");
        config.createSection("parkour." + parkourName + ".checkpoints." + CPName + ".z");

        config.set("parkour." + parkourName + ".checkpoints." + CPName + ".x", p.getLocation().getBlockX());
        config.set("parkour." + parkourName + ".checkpoints." + CPName + ".y", p.getLocation().getBlockY());
        config.set("parkour." + parkourName + ".checkpoints." + CPName + ".z", p.getLocation().getBlockZ());

        config.set("parkour." + parkourName + ".cpcount", config.getInt("parkour." + parkourName + ".cpcount") + 1);
    }


}
