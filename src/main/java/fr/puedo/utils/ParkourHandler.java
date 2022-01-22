package fr.puedo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkourHandler implements CommandExecutor, Listener {

    private Main main;
    public ParkourHandler(Main main){
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] a) {

        FileConfiguration config = main.getConfig();
        Player p = (Player) s;



        switch(a[0].toLowerCase()){
            case "help":
                p.sendMessage(
                        "§6Crackpixel Trial Parkour Plugin \n"
                        + "\n"
                        + "§e/parkour create <parkourname> \n"
                        + "§e/parkour setstart <parkour> §7- Set the start of the parkour to where you stand\n"
                        + "§e/parkour setend <parkour> §7- Set the end of the parkour to where you stand\n"
                        + "§e/parkour teleport <parkour> §7- Teleport you to the start of a parkour\n"
                        + "§e/parkour remove <parkour> §7- Permanently remove a parkour from the config file! §c(NOT IMPLEMENTED YET)");
                break;
            case "create":
                config.createSection("parkour." + a[1] + ".start.x");
                config.createSection("parkour." + a[1] + ".start.y");
                config.createSection("parkour." + a[1] + ".start.z");

                config.createSection("parkour." + a[1] + ".cpcount");
                config.set("parkour." + a[1] + ".cpcount", 0);


                config.createSection("parkour." + a[1] + ".end");
                config.createSection("parkour." + a[1] + ".checkpoints");
                main.saveConfig();
                p.sendMessage(config.getString("CreateMessage") + a[1]);
                break;
            case "setstart":
                config.set("parkour." + a[1] + ".start.x", p.getLocation().getBlockX());
                config.set("parkour." + a[1] + ".start.y", p.getLocation().getBlockY());
                config.set("parkour." + a[1] + ".start.z", p.getLocation().getBlockZ());
                main.saveConfig();
                p.sendMessage(config.getString("SetStartMessage") + a[1]);
                break;
            case "setend":
                config.set("parkour." + a[1] + ".end.x", p.getLocation().getBlockX());
                config.set("parkour." + a[1] + ".end.y", p.getLocation().getBlockY());
                config.set("parkour." + a[1] + ".end.z", p.getLocation().getBlockZ());
                main.saveConfig();
                p.sendMessage(config.getString("SetEndMessage" + a[1]));
                break;
            case "addcheckpoint":
                config.createSection("parkour." + a[1] + ".checkpoint." + config.getInt(""));
            case "teleport":
                if(main.getConfig().contains("parkour." + a[1])){
                    p.teleport(new Location(
                            Bukkit.getWorld("world"),
                            config.getDouble("parkour." + a[1] + ".start.x"),
                            config.getDouble("parkour." + a[1] + ".start.y"),
                            config.getDouble("parkour." + a[1] + ".start.z")
                    ));
                    p.sendMessage(config.getString("TeleportMessage") + a[1]);
                    break;
                } else {
                    p.sendMessage("§cError: this parkour does not exist!");
                    break;
                }
            case "start":
                main.inParkourPlayers.remove(p.getUniqueId());
                if(main.getConfig().contains("parkour." + a[1])){
                    Location totp = new Location(
                            Bukkit.getWorld("world"),
                            config.getDouble("parkour." + a[1] + ".start.x"),
                            config.getDouble("parkour." + a[1] + ".start.y"),
                            config.getDouble("parkour." + a[1] + ".start.z"));

                    p.teleport(totp);
                    p.sendMessage(config.getString("TeleportMessage") + a[1]);

                    main.inParkourPlayers.add(p.getUniqueId());
                    p.sendMessage(config.getString("JoinMessage"));
                    break;
                } else {
                    p.sendMessage("§cError: this parkour does not exist or the starting position isn't correct!");
                    break;
                }
            case "leave":
                main.inParkourPlayers.remove(p.getUniqueId());
                p.sendMessage(config.getString("LeftMessage"));
                break;
            case "remove":
                config.getList("parkour." + a[1]).clear();
                break;
            case "reloadconfig":
                main.reloadConfig();
                p.sendMessage("§aReloaded config with sucess!");
                break;
            case "clearcurrentplayers":
                main.inParkourPlayers.clear();
                p.sendMessage("§cCleared all current players sessions");
                break;
        }
        return false;
    }
}
