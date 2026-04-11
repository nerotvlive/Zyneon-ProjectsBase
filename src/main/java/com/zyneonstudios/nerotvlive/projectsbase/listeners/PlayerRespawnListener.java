package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!(e.isAnchorSpawn() || e.isBedSpawn())) {
            if(p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                Location silberfels = WarpAPI.getWarp("silberfels").getLocation();
                Location rincon = WarpAPI.getWarp("rincon").getLocation();
                int distance_silberfels = (int) silberfels.distance(p.getLocation());
                int distance_rincon = (int) rincon.distance(p.getLocation());
                if (distance_silberfels < distance_rincon) {
                    e.setRespawnLocation(silberfels);
                } else {
                    e.setRespawnLocation(rincon);
                }
            } else {
                if (Main.getUser(p.getUniqueId()).getLastCity().equals("rincon")) {
                    e.setRespawnLocation(WarpAPI.getWarp("rincon").getLocation());
                } else {
                    e.setRespawnLocation(WarpAPI.getWarp("silberfels").getLocation());
                }
            }
        }
    }
}
