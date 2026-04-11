package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!(e.isAnchorSpawn() || e.isBedSpawn())) {
            if (Main.getUser(p.getUniqueId()).getLastCity().equals("rincon")) {
                e.setRespawnLocation(WarpAPI.getWarp("rincon").getLocation());
            } else {
                e.setRespawnLocation(WarpAPI.getWarp("silberfels").getLocation());
            }
        }
    }
}
