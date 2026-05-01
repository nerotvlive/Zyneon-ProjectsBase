package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player p) {
            if (args.length == 0) {
                Communicator.sendError(p,"/shout [Nachricht...]");
            } else {
                User u = Main.getUser(p);
                StringBuilder m= new StringBuilder();
                for (String arg : args) {
                    m.append(arg).append(" ");
                }
                m = new StringBuilder(m.toString().toUpperCase());
                for(Player all: Bukkit.getOnlinePlayers()) {
                    if(p.getWorld()==all.getWorld()) {
                        if(p.getLocation().distance(all.getLocation())<31) {
                            Communicator.sendRaw(all,"§8[§6RP§8] §e"+u.getSelectedCharacter().getJob()+" §f• §e"+u.getSelectedCharacter().getName()+"§8 (SCHREIT) » §7"+m);
                        }
                    }
                }
            }
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }
}
