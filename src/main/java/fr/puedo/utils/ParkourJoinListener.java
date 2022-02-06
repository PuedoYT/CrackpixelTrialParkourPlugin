package fr.puedo.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkourJoinListener implements Listener {

    private final Parkour main;
    public ParkourJoinListener(Parkour main){
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        FileConfiguration config = main.getConfig();

        //TIME CONVERSION

            for(String str : main.getConfig().getConfigurationSection("parkour.").getKeys(false)){
                //if there the player's location isn't equal to a parkour location return
                if(str == null) return;

                    //if the player's loc == a parkour's start loc

                else if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".start.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".start.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".start.z"))))
                {
                    if(config.getList("parkour." + str + ".end") != null)
                    {
                        if(main.inParkourPlayers.containsKey(p.getUniqueId())) return;
                        else {
                            main.inParkourPlayers.put(p.getUniqueId(), System.currentTimeMillis());
                            //main.inParkourPlayers.add(p.getUniqueId());
                            p.sendMessage(main.getConfig().getString("JoinMessage"));
                            config.createSection("timer." + p.getUniqueId() + ".cp_reached");

                            for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false))
                            {
                                config.getConfigurationSection("timer." + p.getUniqueId()).createSection(cpstr);
                                config.set("timer." + p .getUniqueId() + ".cp_reached." + cpstr, false);

                            }
                        }
                    } else p.sendMessage(config.getString("EndPositionNotSet"));


                    //if the player's loc == a parkour's end loc
                } else if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".end.x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".end.y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".end.z"))))
                {
                    if(main.inParkourPlayers.containsKey((p.getUniqueId()))) {
                        Long time = System.currentTimeMillis() - main.inParkourPlayers.get(p.getUniqueId());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                        Date date = new Date(time);
                        String timeSTR = simpleDateFormat.format(date);
                        p.sendMessage(main.getConfig().getString("FinishedParkour") + timeSTR);
                        //TIME CONVERSION
                        //I've put this here so it checks if the player is in the parkour

                        main.inParkourPlayers.remove(p.getUniqueId());
                        config.set("timer." + p.getUniqueId(), null);
                    } else {
                        p.sendMessage(main.getConfig().getString("MustBeInParkour"));
                    }

                } else {
                    //Check for checkpoint(s)
                    for(String cpstr : main.getConfig().getConfigurationSection("parkour." + str + ".checkpoints.").getKeys(false)){
                        if(p.getLocation().getBlockX() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".x") && (p.getLocation().getBlockY() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".y") && (p.getLocation().getBlockZ() == main.getConfig().getDouble("parkour." + str + ".checkpoints." + cpstr + ".z"))))
                        {
                            if(config.getString("timer." + p.getUniqueId() + ".cp_reached").equals(cpstr)) return;
                            else if(main.inParkourPlayers.containsKey(p.getUniqueId())){
                                Long time = System.currentTimeMillis() - main.inParkourPlayers.get(p.getUniqueId());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                                Date date = new Date(time);
                                String timeSTR = simpleDateFormat.format(date);
                                p.sendMessage(config.getString("CheckpointReached") + cpstr + " in " + timeSTR);
                                config.set("timer." + p.getUniqueId() + ".cp_reached", cpstr);
                            } else { p.sendMessage(config.getString("MustBeInParkour")); }
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
