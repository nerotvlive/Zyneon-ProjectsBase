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

public class RoleplayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player player) {
            User u = Main.getUser(player);
            if(!player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                u.setRoleplay(false);
                Communicator.sendInfo(player,"§eRoleplay§8-§eModus§7 deaktiviert");
                return true;
            }
            if(args.length == 0) {
                u.setRoleplay(!u.isRoleplay());
                Communicator.sendInfo(player,"§eRoleplay§8-§eModus§7 aktiviert§8: "+u.isRoleplay());
            } else {
                if(args[0].equalsIgnoreCase("off")||args[0].equalsIgnoreCase("false")) {
                    u.setRoleplay(false);
                    Communicator.sendInfo(player,"§eRoleplay§8-§eModus§7 deaktiviert");
                } else if(args[0].equalsIgnoreCase("on")||args[0].equalsIgnoreCase("true")) {
                    u.setRoleplay(true);
                    Communicator.sendInfo(player,"§eRoleplay§8-§eModus§7 aktiviert");
                } else {
                    Communicator.sendError(s,"/roleplay §7[on/off]");
                }
            }
            u.initListName();
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }
}
