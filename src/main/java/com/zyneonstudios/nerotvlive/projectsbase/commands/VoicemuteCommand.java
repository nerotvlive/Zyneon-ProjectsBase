package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoicemuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s.hasPermission("zyneon.teammode")) {
            if(args.length == 0) {
                Communicator.sendError(s, Strings.playerNotFound);
            } else {
                if(Bukkit.getPlayer(args[0]) != null) {
                    Player t = Bukkit.getPlayer(args[0]);
                } else {
                    Communicator.sendError(s,Strings.playerNotFound);
                }
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }
}