package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.commands.SRLCommand;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Main.onlineUsers.remove(p.getUniqueId());
        User u = Main.getUser(p);
        u.setupCharacter(u.getCharacter());
        e.setJoinMessage("§8» §a"+p.getName());
        if (!u.getJoined()) { welcomePlayer(p, u); }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(SRLCommand.countdown!=null) {
            e.setKickMessage("§cDer Server startet zurzeit neu§8,§c versuch es später erneut§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(Main.maintenance) {
            if(!e.getPlayer().hasPermission("zyneon.team")) {
                e.setKickMessage("§cDer Server ist zurzeit im Wartungsmodus§8,§c versuch es später erneut§8!");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            }
        } else if(!Main.whitelist.contains(e.getPlayer().getUniqueId().toString())) {
            e.setKickMessage("§cDu darfst auf diesem Server nicht spielen§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    public static void welcomePlayer(Player p, User u) {
        // Wir lagern die Datenbank-Suche in einen asynchronen Thread aus
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            boolean teleported = false;
            String finalWarpName = null;

            while (!teleported) {
                String city = Math.random() > 0.5 ? "rincon" : "silberfels";
                String warpNumber = (city.equals("rincon")) ? ((int)(Math.random() * 16)) + "" : ((int)(Math.random() * 30)) + "";
                String warpString = city + "Hotel" + warpNumber;

                // Diese Aufrufe blockieren jetzt nur den Async-Thread, nicht den Server
                if (WarpAPI.ifWarpExists(warpString) && WarpAPI.isWarpEnabled(warpString)) {
                    finalWarpName = warpString;
                    teleported = true;
                }
            }

            // Da Teleportation SYNC sein muss, springen wir zurück zum Main-Thread
            String finalName = finalWarpName;
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                if (p.isOnline()) { // Immer prüfen, ob der Spieler noch da ist!
                    p.teleport(WarpAPI.getWarp(finalName));
                    WarpAPI.disableWarp(finalName);
                    u.setJoined(true);
                }
            });
        });
    }
}