package fr.puedo.utils.ParkourUtils;

import fr.puedo.utils.Parkour;
import org.bukkit.configuration.file.FileConfiguration;

public class createParkour {
    private Parkour main;
    public createParkour(Parkour main, String parkourName) {
        FileConfiguration config = main.getConfig();
        this.main = main;
        config.createSection("parkour." + parkourName + ".start.x");
        config.createSection("parkour." + parkourName + ".start.y");
        config.createSection("parkour." + parkourName + ".start.z");

        config.createSection("parkour." + parkourName + ".end");

        config.createSection("parkour." + parkourName + ".cpcount");
        config.set("parkour." + parkourName + ".cpcount", 0);

        config.createSection("timer");

        config.createSection("parkour." + parkourName + ".checkpoints");
        main.saveConfig(); }


}
