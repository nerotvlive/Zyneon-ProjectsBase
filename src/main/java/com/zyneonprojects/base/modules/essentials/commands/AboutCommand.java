package com.zyneonprojects.base.modules.essentials.commands;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.modules.loader.commands.ModulesCommand;
import com.zyneonprojects.base.modules.loader.modules.ProjectsBaseModule;
import com.zyneonprojects.base.utilities.Communicator;
import com.zyneonprojects.base.utilities.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AboutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(args.length == 0) {
            Communicator.sendRaw(s,"§r §r");
            Communicator.sendRaw(s,Communicator.getPrefix()+"§fSystem §9§l\"ProjectsBase\"§r§8:§r");
            Communicator.sendRaw(s,Communicator.getPrefix()+"§a⬤ running §8(static)§r");
            Communicator.sendInfo(s, "§r §r");
            Communicator.sendRaw(s,Communicator.getPrefix()+"§7Identifier§8: §rProjectsBase§8@v§r"+ProjectsBase.getInstance().getVersion());
            Communicator.sendRaw(s,Communicator.getPrefix()+"§7Author§8(§7s§8): §r"+ProjectsBase.getInstance().getAuthor());
            Communicator.sendRaw(s,Communicator.getPrefix()+"§7Description§8: §r"+ProjectsBase.getInstance().getPlugin().getPluginMeta().getDescription());
            if(Permissions.has(s, "projectsbase.module.modules.command.modules")) {
                StringBuilder modules = new StringBuilder("§amodules");
                for(ProjectsBaseModule module : ProjectsBase.getInstance().getModuleLoader().getModules()) {
                    String prefix = "§8, §f" + ModulesCommand.getModuleStateColor(module.getActivationStatus());
                    modules.append(prefix).append(module.getId());
                }
                Communicator.sendRaw(s,Communicator.getPrefix()+"§7Module§8(§7s§8): "+modules+"§r");
            }
            Communicator.sendRaw(s,"§r §r");
        } else {
            if(Permissions.has(s, "projectsbase.module.modules.command.modules")) {
                if(args[0].equals("list")||!Permissions.has(s, "projectsbase.module.modules.command.modules.module")) {
                    Bukkit.dispatchCommand(s, "about");
                } else {
                    if(ProjectsBase.getInstance().getModuleLoader().getModuleIds().contains(args[0])) {
                        ModulesCommand.sendModuleInfo(s,ProjectsBase.getInstance().getModuleLoader().getModule(args[0]));
                    } else {
                        Communicator.sendErr(s,"Couldn't find module by id §4\""+args[0]+"\"§8!");
                    }
                }
            } else {
                Bukkit.dispatchCommand(s, "about");
            }
        }
        return true;
    }
}
