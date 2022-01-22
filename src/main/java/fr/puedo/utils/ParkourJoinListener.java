package fr.puedo.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParkourJoinListener implements Listener {

    private Main main;
    public ParkourJoinListener(Main main){
        this.main = main;
    }

    private ParkourHandler prkh;
    public ParkourJoinListener(ParkourHandler prkh) { this.prkh = prkh; }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();

        for(String str : main.getConfig().getConfigurationSection("parkour.").getKeys(false)){
            if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".start.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".start.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".start.z")))){
                if(main.inParkourPlayers.contains(p.getUniqueId())) p.sendMessage(main.getConfig().getString("AlreadyInParkour"));
                else {
                    main.inParkourPlayers.add(p.getUniqueId());
                    p.sendMessage(main.getConfig().getString("JoinMessage"));
                    main.getConfig().createSection("timer." + p.getUniqueId());
                }

            } else if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".end.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".end.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".end.z")))){
                if(main.inParkourPlayers.contains(p.getUniqueId())) p.sendMessage(main.getConfig().getString("FinishedParkour"));
                else {
                    p.sendMessage(main.getConfig().getString("MustBeInParkour"));
                }

            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(main.inParkourPlayers.contains(p.getUniqueId())){
            main.inParkourPlayers.remove(p.getUniqueId());
        }
    }

}
