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
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length==0) {
                if(s instanceof Player p) {
                    Communicator.sendInfo(p, "§7Du bist im §e"+p.getGameMode().name()+"§8-§eModus§8.");
                } else {
                    Communicator.sendError(s, Strings.needPlayer);
                }
            } else if(args.length==1) {
                if(s instanceof Player p) {
                    User u = Main.getUser(p);
                    String gm = args[0];
                    if(gm.equalsIgnoreCase("0")||gm.equalsIgnoreCase("s")||gm.equalsIgnoreCase("survival")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        Communicator.sendInfo(p, "§7Du bist nun im §eSurvival§8-§eModus§8.");
                    } else if(gm.equalsIgnoreCase("1")||gm.equalsIgnoreCase("c")||gm.equalsIgnoreCase("creative")) {
                        p.setGameMode(GameMode.CREATIVE);
                        Communicator.sendInfo(p, "§7Du bist nun im §eCreative§8-§eModus§8.");
                    } else if(gm.equalsIgnoreCase("2")||gm.equalsIgnoreCase("a")||gm.equalsIgnoreCase("adventure")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        Communicator.sendInfo(p, "§7Du bist nun im §eAdventure§8-§eModus§8.");
                    } else if(gm.equalsIgnoreCase("3")||gm.equalsIgnoreCase("z")||gm.equalsIgnoreCase("sp")||gm.equalsIgnoreCase("spectator")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        Communicator.sendInfo(p, "§7Du bist nun im §eSpectator§8-§eModus§8.");
                    } else {
                        Communicator.sendError(s, "/gamemode [0/s/survival/1/c/creative/2/a/adventure/3/z/sp/spectator] §7[Spieler]");
                    }
                    u.initListName();
                } else {
                    Communicator.sendError(s, Strings.needPlayer);
                }
            } else {
                if(Bukkit.getPlayer(args[1])!=null) {
                    Player p = Bukkit.getPlayer(args[1]);
                    User u = Main.getUser(p);
                    String gm = args[0];
                    if(gm.equalsIgnoreCase("0")||gm.equalsIgnoreCase("s")||gm.equalsIgnoreCase("survival")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        Communicator.sendInfo(p, "§7Du bist nun im §eSurvival§8-§eModus§8.");
                        Communicator.sendInfo(s, "§7Du hast §e"+p.getName()+"§7 in den §aSurvival§8-§aModus §7gesetzt§8.");
                    } else if(gm.equalsIgnoreCase("1")||gm.equalsIgnoreCase("c")||gm.equalsIgnoreCase("creative")) {
                        p.setGameMode(GameMode.CREATIVE);
                        Communicator.sendInfo(p, "§7Du bist nun im §eCreative§8-§eModus§8.");
                        Communicator.sendInfo(s, "§7Du hast §e"+p.getName()+"§7 in den §aCreative§8-§aModus §7gesetzt§8.");
                    } else if(gm.equalsIgnoreCase("2")||gm.equalsIgnoreCase("a")||gm.equalsIgnoreCase("adventure")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        Communicator.sendInfo(p, "§7Du bist nun im §eAdventure§8-§eModus§8.");
                        Communicator.sendInfo(s, "§7Du hast §e"+p.getName()+"§7 in den §aAdventure§8-§aModus §7gesetzt§8.");
                    } else if(gm.equalsIgnoreCase("3")||gm.equalsIgnoreCase("z")||gm.equalsIgnoreCase("sp")||gm.equalsIgnoreCase("spectator")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        Communicator.sendInfo(p, "§7Du bist nun im §eSpectator§8-§eModus§8.");
                        Communicator.sendInfo(s, "§7Du hast §e"+p.getName()+"§7 in den §aSpectator§8-§aModus §7gesetzt§8.");
                    } else {
                        Communicator.sendError(s, "/gamemode [0/s/survival/1/c/creative/2/a/adventure/3/z/sp/spectator] §7[Spieler]");
                    }
                    u.initListName();
                } else {
                    Communicator.sendError(s, Strings.playerNotFound);
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(s.hasPermission("zyneon.team")) {
            if(args.length==1) {
                completer.add("0");
                completer.add("s");
                completer.add("survival");
                completer.add("1");
                completer.add("c");
                completer.add("creative");
                completer.add("2");
                completer.add("a");
                completer.add("adventure");
                completer.add("3");
                completer.add("z");
                completer.add("sp");
                completer.add("spectator");
            } else if(args.length==2) {
                for(Player all:Bukkit.getOnlinePlayers()) {
                    String name = all.getName();
                    if(name.toLowerCase().contains(args[1].toLowerCase())) {
                        completer.add(all.getName());
                    }
                }
            }
        }
        return completer;
    }
}