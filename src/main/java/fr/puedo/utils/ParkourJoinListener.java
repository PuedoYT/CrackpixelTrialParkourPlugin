package fr.puedo.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParkourJoinListener implements Listener {

    private final Main main;
    public ParkourJoinListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        FileConfiguration config = main.getConfig();
        for(String str : main.getConfig().getConfigurationSection("parkour.").getKeys(false)){
            //if there the player's location isn't equal to a parkour location return
            if(str == null) return;
            //if the player's loc == a parkour's start loc
            else if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".start.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".start.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".start.z"))))
            {
                if(main.inParkourPlayers.contains(p.getUniqueId())) return;
                else {
                    main.inParkourPlayers.add(p.getUniqueId());
                    p.sendMessage(main.getConfig().getString("JoinMessage"));
                    config.createSection("timer." + p.getUniqueId() + ".cp_reached");
                    config.getConfigurationSection("timer." + p.getUniqueId()).createSection(".ctimer");

                    for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false))
                    {
                        config.getConfigurationSection("timer." + p.getUniqueId()).createSection(cpstr);
                        config.set("timer." + p .getUniqueId() + ".cp_reached." + cpstr, false);
                    }
                }

            //if the player's loc == a parkour's end loc
            } else if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".end.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".end.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".end.z"))))
            {
                if(main.inParkourPlayers.contains(p.getUniqueId())) {
                    p.sendMessage(main.getConfig().getString("FinishedParkour"));
                    main.inParkourPlayers.remove(p.getUniqueId());
                    config.getConfigurationSection("timer." + p.getUniqueId());
                } else {
                    p.sendMessage(main.getConfig().getString("MustBeInParkour"));
                }

            } else {
                for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false)){
                    if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".z"))))
                    {
                        if(config.getString("timer." + p.getUniqueId() + ".cp_reached").equals(cpstr)) return;
                        else {
                            p.sendMessage("§bYou reached the checkpoint: " + cpstr);
                            config.set("timer." + p.getUniqueId() + ".cp_reached", cpstr);
                        }
                    }
                }
            }
        }


        //
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        main.inParkourPlayers.remove(p.getUniqueId());
    }

}
