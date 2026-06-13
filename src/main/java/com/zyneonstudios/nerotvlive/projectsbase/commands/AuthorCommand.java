package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class AuthorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        Communicator.sendRaw(s,Strings.prefix+"§8=====================================");
        Communicator.sendRaw(s,Strings.prefix+"§7Plugin§8: §fZyneon ProjectsBase "+ Main.version);
        Communicator.sendRaw(s, Strings.prefix+"§7Versions-Titel§8: §fFast-Gut Edition");
        Communicator.sendRaw(s,Strings.prefix+"§7Authors§8: §eBlack_0mega§8, §aDerFantastico§8,");
        Communicator.sendRaw(s,Strings.prefix+"§7            §cnerotvlive");
        Communicator.sendRaw(s,Strings.prefix+"§8=====================================");
        if(s instanceof Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
        return false;
    }
}
