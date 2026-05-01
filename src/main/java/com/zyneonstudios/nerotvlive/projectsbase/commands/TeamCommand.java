package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
                    Communicator.sendInfo(p,"Teammodus§8: §e"+u.isTeamMode());
                    p.setOp(u.isTeamMode());
                    if(!u.isTeamMode()) {
                        p.setGameMode(GameMode.SURVIVAL);
                    }
                } else {
                    if(s.hasPermission("zyneon.team")) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            User tU = Main.getUser(t);
                            tU.setTeamMode(!tU.isTeamMode());
                            Communicator.sendError(t, "Teammodus§8: §e"+tU.isTeamMode());
                            Communicator.sendInfo(p,"Teammodus§8: §e"+tU.isTeamMode()+" für "+t.getName());
                            t.setOp(tU.isTeamMode());
                            if(!tU.isTeamMode()) {
                                t.setGameMode(GameMode.SURVIVAL);
                            }
                        } else {
                            Communicator.sendError(p, Strings.playerNotFound);
                        }
                    } else {
                        Communicator.sendError(p, Strings.noPermission);
                    }
                }
            } else {
                Communicator.sendError(p, Strings.farmWorldName);
            }
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }
}
