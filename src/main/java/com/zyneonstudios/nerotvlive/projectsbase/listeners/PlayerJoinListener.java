package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.commands.SRLCommand;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User u = Main.getUser(p);
        u.setupCharacter(u.getCharacter());
        e.setJoinMessage("§8» §a"+p.getName());
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
}