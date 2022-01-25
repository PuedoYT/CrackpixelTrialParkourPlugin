package fr.puedo.utils;

import fr.puedo.utils.ParkourUtils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ParkourHandler implements CommandExecutor, Listener {

    private final Main main;
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
                new createParkour(main ,a[1]);
                p.sendMessage(config.getString("CreateMessage") + a[1]);
                break;
            case "setstart":
                new setStart(main, p, a[1]);
                p.sendMessage(config.getString("SetStartMessage") + a[1]);
                break;
            case "setend":
                new setEnd(main, p, a[1]);
                p.sendMessage(config.getString("SetEndMessage" + a[1]));
                break;
            case "addcheckpoint":
                new createCheckpoint(main, a[1], config.getInt("parkour." + a[1] + ".cpcount"), p);
                p.sendMessage("§aAdded checkpoint!");
                main.saveConfig();
                break;
            case "teleport":
                new teleport(main, p, a[1]);
                break;
            case "start":
                main.inParkourPlayers.remove(p.getUniqueId());
                new teleport(main, p, a[1]);
            case "leave":
                if(main.inParkourPlayers.contains(p.getUniqueId())){
                    main.inParkourPlayers.remove(p.getUniqueId());
                    p.sendMessage(config.getString("LeftMessage"));
                } else { p.sendMessage("§cYou are not in a parkour."); }
                break;
            case "rtcp":
                for(String str : main.getConfig().getConfigurationSection("parkour.").getKeys(false)){
                    for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false))
                    {
                        if(config.getString("timer." + p.getUniqueId() + ".cp_reached").equals(cpstr))
                        {
                            p.teleport(new Location(Bukkit.getWorld("world"),
                                    config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".x") + 0.5,
                                    config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".y"),
                                    config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".z") + 0.5));
                            p.sendMessage("§bTeleported to the previous checkpoint.");
                        }
                    }
                }
                break;
            case "remove":
                if(config.contains("parkour." + a[1])) config.getList("parkour." + a[1]).clear();
                else p.sendMessage("§cParkour not found.");
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
