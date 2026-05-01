package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SearchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(s.hasPermission("zyneon.search")) {
            if(s instanceof Player p) {
                if(p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                    User u = Main.getUser(p);
                    if(u.isRoleplay()) {
                        if(p.getWorld().getPlayers().size() > 1) {
                            boolean found = false;
                            for(Entity e:p.getNearbyEntities(1,1,1)) {
                                if(e instanceof Player t) {
                                    if(t.getLocation().distance(p.getLocation()) <= 0.75) {
                                        p.openInventory(t.getInventory());
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if(!found) {
                                Communicator.sendError(s, Strings.playerNotFound);
                            }
                        } else {
                            Communicator.sendError(s, Strings.playerNotFound);
                        }
                    } else {
                        Communicator.sendError(s, "§cDieser Befehl darf nur im RP-Modus verwendet werden§8! (Und du darfst ihn nicht abusen! Wir überwachen und loggen die Nutzung.)");
                    }
                } else {
                    Communicator.sendError(s, "§cDu musst dazu in der RP Welt sein§8!");
                }
            } else {
                Communicator.sendError(s, Strings.needPlayer);
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }
}