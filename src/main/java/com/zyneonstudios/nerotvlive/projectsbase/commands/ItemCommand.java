package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.custom.CustomItems;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import com.zyneonstudios.nerotvlive.projectsbase.weapons.WeaponItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        Communicator.sendError(s,"§c/item <custom_item_id> §7<amount>");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length > 0) {
                int amount = 1;
                if(args.length > 1) {
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sendSyntax(s);
                        return false;
                    }
                }
                String id = args[0].toLowerCase();
                if(WeaponItems.getWeapons().containsKey(id)||CustomItems.getCustomItems().containsKey(id)) {
                    if(s instanceof Player p) {
                        ItemStack item;
                        if(WeaponItems.getWeapons().containsKey(id)) {
                            item = WeaponItems.getWeapons().get(id);
                        } else {
                            item = CustomItems.getCustomItems().get(id);
                        }
                        p.getInventory().addItem(item.clone());
                    } else {
                        Communicator.sendError(s, Strings.needPlayer);
                    }
                }
            } else {
                sendSyntax(s);
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("zyneon.team")) {
            if(args.length == 1) {
                ArrayList<String> list = new ArrayList<>(WeaponItems.getWeapons().keySet());
                list.addAll(CustomItems.getCustomItems().keySet());
                return list;
            }
        }
        return List.of();
    }
}