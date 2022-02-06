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

    private final Parkour main;
    public ParkourHandler(Parkour main){
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
                        + "§e/parkour remove <parkour> §7- Permanently remove a parkour from the config file \n"
                        + "§e/parkour addcheckpoint <parkour> §7- Add a checkpoint \n"
                        + "§e/parkour start <parkour> §7- Teleports you to the parkour and automatically start it.\n"
                        + "§e/parkour leave §7- Leave the parkour you are currently in\n"
                        + "§e/parkour rtcp §7- Teleport you to the previous checkpoint\n"
                        + "§e/parkour reload §7- Reload the config.\n"
                        + "§e/parkour clearsessions §7- Clear all current parkour sessions\n");
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
                p.sendMessage(config.getString("AddedCheckpoint"));
                main.saveConfig();
                break;
            case "teleport":
                new teleport(main, p, a[1]);
                break;
            case "start":
                main.inParkourPlayers.remove(p.getUniqueId());
                new teleport(main, p, a[1]);
            case "leave":
                if(main.inParkourPlayers.containsKey(p.getUniqueId())){
                    main.inParkourPlayers.remove(p.getUniqueId());
                    p.sendMessage(config.getString("LeftMessage"));
                } else { p.sendMessage(config.getString("MustBeInParkour")); }
                break;
            case "rtcp":
                for(String str : main.getConfig().getConfigurationSection("parkour.").getKeys(false)){
                    for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false))
                    {
                        if(main.inParkourPlayers.containsKey(p.getUniqueId())){
                            if(config.getString("timer." + p.getUniqueId() + ".cp_reached").equals(cpstr))
                            {
                                p.teleport(new Location(Bukkit.getWorld("world"),
                                        config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".x") + 0.5,
                                        config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".y"),
                                        config.getDouble("parkour." + str + ".checkpoints." + cpstr + ".z") + 0.5));
                                p.sendMessage(config.getString("TeleportedToCP"));
                            }
                        } else p.sendMessage(config.getString("MustBeInParkour")); break;
                    }
                }
                break;
            case "remove":
                if(config.contains("parkour." + a[1])) {
                    config.set("parkour." + a[1], null);
                    main.saveConfig();
                    p.sendMessage(config.getString("DeletedParkour") + a[1]);}
                else p.sendMessage(config.getString("ParkourNotFound"));
                break;
            case "reload":
                main.reloadConfig();
                p.sendMessage("§aReloaded config with sucess!");
                break;
            case "clearsessions":
                main.inParkourPlayers.clear();
                for(String str : main.getConfig().getConfigurationSection("timer.").getKeys(false)){
                    config.set("parkour." + str, null);
                }
                p.sendMessage("§cCleared all current players sessions");
                break;
        }
        return false;
    }
}
