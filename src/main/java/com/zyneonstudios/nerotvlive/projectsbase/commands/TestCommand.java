package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if (s instanceof Player p) {
                Communicator.sendError(s, Strings.noPermission);
            } else {
                Communicator.sendError(s, Strings.needPlayer);
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }
}
