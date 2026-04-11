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

public class WhisperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player p) {
            if (args.length == 0) {
                Communicator.sendError(p,"/shout [Nachricht...]");
            } else {
                User u = Main.getUser(p);
                String m="";
                for(int i=0;i<args.length;i++) {
                    m=m+args[i]+" ";
                }
                m=m.toLowerCase();
                for(Player all: Bukkit.getOnlinePlayers()) {
                    if(p.getWorld()==all.getWorld()) {
                        if(p.getLocation().distance(all.getLocation())<3) {
                            Communicator.sendRaw(all,"§8[§6RP§8] §6"+u.getSelectedCharacter().getJob()+" §f• §e"+u.getSelectedCharacter().getName()+" (flüstert) » §7"+m);
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
