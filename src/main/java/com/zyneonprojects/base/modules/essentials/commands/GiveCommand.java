package com.zyneonprojects.base.modules.essentials.commands;

import com.zyneonprojects.base.database.ItemDatabase;
import com.zyneonprojects.base.utilities.Communicator;
import com.zyneonprojects.base.utilities.Permissions;
import com.zyneonprojects.base.utilities.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiveCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(Permissions.has(s, "projectsbase.module.essentials.command.give")) {
            if(args.length == 1) {
                String id = args[0].toLowerCase().replace("minecraft:", "");
                if(Permissions.has(s, "projectsbase.module.essentials.command.give."+id)) {
                    if(s instanceof Player p) {
                        if(ItemDatabase.get(id)!=null) {
                            if(p.getInventory().firstEmpty() == -1) {
                                Communicator.sendErr(Strings.get(Strings.Key.error_inventory_full));
                            } else {
                                p.getInventory().addItem(ItemDatabase.get(id));
                                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                            }
                        } else {
                            Communicator.sendErr(Strings.get(Strings.Key.error_item_not_found));
                        }
                    } else {
                        Communicator.sendErr(Strings.get(Strings.Key.error_players_only));
                    }
                } else {
                    Communicator.sendErr(Strings.get(Strings.Key.error_no_permission));
                }
            } else if(args.length == 2) {
                if(ItemDatabase.get(args[0].toLowerCase().replace("minecraft:", ""))!=null) {
                    try {
                        String id = args[0].toLowerCase().replace("minecraft:", "");
                        int amount = Integer.parseInt(args[1]);
                        if(amount <= 0) {
                            amount = 1;
                        } else if(amount > 64) {
                            amount = 64;
                        }
                        if(s instanceof Player p) {
                            if(ItemDatabase.get(id)!=null) {
                                if(p.getInventory().firstEmpty() == -1) {
                                    Communicator.sendErr(Strings.get(Strings.Key.error_inventory_full));
                                } else {
                                    p.getInventory().addItem(ItemDatabase.get(id, amount));
                                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                                }
                            } else {
                                Communicator.sendErr(Strings.get(Strings.Key.error_item_not_found));
                            }
                        } else {
                            Communicator.sendErr(Strings.get(Strings.Key.error_players_only));
                        }
                    } catch (Exception e) {
                        Communicator.sendErr(Strings.get(Strings.Key.error_invalid_amount));
                    }
                } else {
                    if(Permissions.has(s, "projectsbase.module.essentials.command.give.others")) {

                    } else {
                        Communicator.sendErr(Strings.get(Strings.Key.error_item_not_found));
                    }
                }
            }
        } else {
            Communicator.sendErr(Strings.get(Strings.Key.error_no_permission));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(Permissions.has(s, "projectsbase.module.essentials.command.give")) {
            ArrayList<String> list = new ArrayList<>();
            if(args.length == 1) {
                list.add("@n");
                list.add("@s");
                if(s instanceof Player p) {
                    list.add(p.getName());
                }
                list.add("@p");
                for(String itemId: ItemDatabase.getItems()) {
                    if(Permissions.has(s, "projectsbase.module.essentials.command.give." + itemId.toLowerCase().replace("minecraft:", ""))) {
                        list.add(itemId.toLowerCase());
                    }
                }
                if(Permissions.has(s, "projectsbase.module.essentials.command.give.others")) {
                    list.add("@r");
                    list.add("@a");
                    list.add("@e");
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        list.add(player.getName());
                    }
                }
            } else if(args.length == 2) {
                if(ItemDatabase.get(args[0])!=null) {
                    int max;
                    try {
                        max = Objects.requireNonNull(ItemDatabase.get(args[0])).getMaxStackSize();
                    } catch (Exception e) {
                        max = 64;
                    }
                    for(int i = 1; i <= max; i++) {
                        list.add(String.valueOf(i));
                    }
                } else {
                    if(Permissions.has(s, "projectsbase.module.essentials.command.give.others")) {
                        for(String itemId: ItemDatabase.getItems()) {
                            if(Permissions.has(s, "projectsbase.module.essentials.command.give.others." + itemId.toLowerCase().replace("minecraft:", ""))) {
                                list.add(itemId.toLowerCase());
                            }
                        }
                    }
                }
            } else if(args.length == 3) {
                int max;
                try {
                    max = Objects.requireNonNull(ItemDatabase.get(args[1])).getMaxStackSize();
                } catch (Exception e) {
                    max = 64;
                }
                for(int i = 1; i <= max; i++) {
                    list.add(String.valueOf(i));
                }
            }
            for (String item : list) {
                if (item.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completer.add(item.toLowerCase());
                }
            }
        }
        return completer;
    }
}
