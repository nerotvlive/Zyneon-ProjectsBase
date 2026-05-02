package com.zyneonstudios.nerotvlive.projectsbase.locks.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.LockManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PBUnlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(LockManager.enableLocks()) {
            if (s instanceof Player p) {
                Communicator.sendError(p, "Bedenke, dass dieses Locksystem nur noch bis Freitag funktioniert! Benutze die normalen /lock, /trust etc. Befehle, schaue dafür in #primal4-wichtig auf dem DC!");
                User u = Main.getUser(p);
                u.setInteractMode("unlocking");
                Communicator.sendInfo(p, "Klicke den Block an§8,§7 den du entsichern möchtest§8...");
            } else {
                Communicator.sendError(s, Strings.needPlayer);
            }
        }
        return false;
    }
}
