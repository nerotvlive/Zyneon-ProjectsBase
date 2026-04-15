package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Countdown;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class TPACommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(s instanceof Player p) {
            if(p.getLocation().getWorld().getName().equalsIgnoreCase(Strings.farmWorldName)) {
                if(cooldown.contains(p.getUniqueId())) {
                    Communicator.sendError(p, "§cDu kannst diesen Befehl nur alle 10 Minuten benutzen§8!");
                    return false;
                }
                Communicator.sendError(p,"§cNicht fertig implementiert§8...");
                //TODO tpa request and accept
            } else {
                Communicator.sendError(p, "§cDu kannst diesen Befehl nur in der Farmwelt verwenden§8!");
            }
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }

    public static ArrayList<UUID> cooldown = new ArrayList<>();
    public static void startWarpCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if(!cooldown.contains(uuid)) {
            cooldown.add(uuid);
            new Countdown(600, Main.getInstance()) {
                @Override
                public void count(int current) {
                    if(current==1) {
                        cooldown.remove(uuid);
                    }
                }
            }.start();
        }
    }
}
