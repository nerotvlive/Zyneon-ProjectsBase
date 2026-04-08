package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        if (e.getBlock().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (e.getEntity().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        if (e.getEntity().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }
}