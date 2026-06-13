package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

//no, it's totally fine to use a default String
@SuppressWarnings("deprecation")
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        try {
            Objects.requireNonNull(p.getScoreboard().getTeam(p.getUniqueId().toString())).unregister();
        } catch (Exception ignore) {}
        p.setOp(false);
        Main.onlineUsers.remove(p.getUniqueId());
        e.setQuitMessage("§8« §c"+p.getName());
    }
}