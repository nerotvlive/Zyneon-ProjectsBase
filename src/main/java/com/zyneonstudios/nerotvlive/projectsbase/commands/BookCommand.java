package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BookCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            if(s instanceof Player p) {
                if(p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                } else {
                    Communicator.sendError(s, "§cDein Inventar ist voll§8!");
                }
            } else {
                Communicator.sendError(s, Strings.needPlayer);
            }
        } else {
            if(s.hasPermission("zyneon.team")) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if(p.getInventory().firstEmpty() != -1) {
                        p.getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                    } else {
                        Communicator.sendError(s, "§cDas Inventar von §4"+p.getName()+"§c ist voll§8!");
                    }
                } else {
                    Communicator.sendError(s, Strings.playerNotFound);
                }
            } else {
                Communicator.sendError(s, Strings.noPermission);
            }
        }
        return false;
    }
}