package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player p) {
            if(p.hasPermission("zyneon.teammode")) {
                if(args.length == 0) {
                    User u = Main.getUser(p);
                    u.setTeamMode(!u.isTeamMode());
                    p.setOp(u.isTeamMode());
                    Communicator.sendInfo(p,"Teammodus§8: §e"+u.isTeamMode());
                }
            } else {
                Communicator.sendError(p, Strings.noPermission);
            }
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }
}
