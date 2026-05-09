package com.zyneonprojects.base.modules.loader.commands;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.modules.loader.modules.ProjectsBaseModule;
import com.zyneonprojects.base.utilities.Communicator;
import com.zyneonprojects.base.utilities.Permissions;
import com.zyneonprojects.base.utilities.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModulesCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!ProjectsBase.getInstance().getConfig().getBoolean("settings.modules.loader.enable")) { return false; }
        if(Permissions.has(s, "projectsbase.module.modules.command.modules")) {
            if(args.length == 0) {
                if(ProjectsBase.getInstance().getModuleLoader().getModule("essentials") != null) {
                    Bukkit.dispatchCommand(s, "about");
                } else {
                    Bukkit.dispatchCommand(s, "modules list");
                }
            } else {
                if(!Permissions.has(s,"projectsbase.module.modules.command.modules.module")||args[0].equalsIgnoreCase("list")) {
                    sendModuleInfo(s,ProjectsBase.getInstance().getModuleLoader());
                    Communicator.sendInfo(s, "§b§lProjectsBase §r§fModules§8:");
                    StringBuilder modules = new StringBuilder("§amodules");
                    for(ProjectsBaseModule module : ProjectsBase.getInstance().getModuleLoader().getModules()) {
                        String prefix = "§8, §f" + getModuleStateColor(module.getActivationStatus());
                        modules.append(prefix).append(module.getId());
                    }
                    Communicator.sendInfo(s, modules.toString());
                    Communicator.sendInfo(s, "§f/modules <moduleId>§7 to see more info about a module");
                    Communicator.sendRaw(s, " ");
                } else {
                    if(args[0].equals("modules")) {
                        sendModuleInfo(s,ProjectsBase.getInstance().getModuleLoader());
                        Communicator.sendRaw(s, " ");
                    } else if(ProjectsBase.getInstance().getModuleLoader().getModuleIds().contains(args[0])) {
                        sendModuleInfo(s,ProjectsBase.getInstance().getModuleLoader().getModule(args[0]));
                        Communicator.sendRaw(s, " ");
                    } else {
                        Communicator.sendErr(s,"Module with id §4\""+args[0]+"\"§c not found§8!");
                    }
                }
            }
            return true;
        } else {
            Communicator.sendErr(s, Strings.get(Strings.Key.error_no_permission));
            return false;
        }
    }

    public static void sendModuleInfo(CommandSender s, ProjectsBaseModule module) {
        String prefix = getModuleStateColor(module.getActivationStatus());
        Communicator.sendRaw(s, " ");
        Communicator.sendInfo(s, "§fModule §r§l"+prefix+"§l\""+module.getName()+"\"§8:");
        Communicator.sendInfo(s, prefix+"⬤ "+module.getActivationStatus().toString().toLowerCase()+"§r§8 ("+module.getModuleFormat().toString().toLowerCase()+")");
        Communicator.sendInfo(s, " ");
        Communicator.sendInfo(s, "§7Identifier§8: §f"+module.getId()+"§8@v§f"+module.getVersion());
        Communicator.sendInfo(s, "§7Author§8(§7s§8): §f"+module.getAuthor());
        Communicator.sendInfo(s, "§7Description§8: §f"+module.getDescription());
    }

    public static String getModuleStateColor(ProjectsBase.ActivationStatus status) {
        return switch (status) {
            case UNLOADED, UNINITIALIZED, UNLOADING -> "§8";
            case CRASHED                            -> "§4";
            case DISABLING, DISABLED                -> "§c";
            case LOADING                            -> "§3";
            case LOADED                             -> "§b";
            case ENABLING                           -> "§2";
            case ENABLED                            -> "§a";
            default                                 -> "§6";
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            list.add("list");
            if (Permissions.has(sender, "projectsbase.module.modules.command.modules.module")) {
                list.add("modules");
                list.addAll(ProjectsBase.getInstance().getModuleLoader().getModuleIds());
            }
            for (String module : list) {
                if (module.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completer.add(module);
                }
            }
        }
        return completer;
    }
}
