package com.zyneonstudios.nerotvlive.projectsbase.api;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Countdown;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.Storage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class WarpAPI {

    private static Storage warps;

    public static void initAPI() {
        if (Main.storage.getStorageType() == Storage.storageType.YAML) {
            warps = new Storage("plugins/ProjectsBase/warps.yml");
        } else if (Main.storage.getStorageType() == Storage.storageType.SQLite) {
            warps = new Storage("plugins/ProjectsBase/warps.db");
            warps.setupTable("warps", 1);
        } else {
            warps = Main.storage;
            warps.setupTable("warps", 1);
        }
    }

    public static boolean ifWarpExists(String warpName) {
        String name = warpName.toLowerCase();
        return !warps.getString("warps", name + ".e", 0).equalsIgnoreCase("null");
    }

    public static boolean isWarpEnabled(String warpName) {
        String name = warpName.toLowerCase();
        if (ifWarpExists(name)) {
            return warps.getString("warps", name + ".e", 0).equalsIgnoreCase("true");
        } else {
            return false;
        }
    }

    public static void setWarp(String warpName, World w, double x, double y, double z, float yaw, float pitch, boolean enable) {
        warps.set("warps", warpName.toLowerCase() + ".w", w.getName());
        warps.set("warps", warpName.toLowerCase() + ".x", x);
        warps.set("warps", warpName.toLowerCase() + ".y", y);
        warps.set("warps", warpName.toLowerCase() + ".z", z);
        warps.set("warps", warpName.toLowerCase() + ".a", yaw);
        warps.set("warps", warpName.toLowerCase() + ".p", pitch);
        warps.set("warps", warpName.toLowerCase() + ".e", enable + "");

        ArrayList<String> warpList = new ArrayList<>();
        if (warps.getString("warps", "warpList.array", 0) != null) {
            String listString = warps.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        }
        if (!warpList.contains(warpName.toLowerCase())) {
            warpList.add(warpName.toLowerCase());
        }
        warps.set("warps", "warpList.array", warpList.toString());
    }

    public static void setWarp(String Warpname, Player p, boolean enable) {
        Warpname = Warpname.toLowerCase();
        setWarp(Warpname, p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), enable);
    }

    public static Location getWarp(String warpName) {
        String name = warpName.toLowerCase();
        return new Location(Bukkit.getWorld(warps.getString("warps", name + ".w", 0)), warps.getDouble("warps", name + ".x", 0), warps.getDouble("warps", name + ".y", 0), warps.getDouble("warps", name + ".z", 0), (float) warps.getDouble("warps", name + ".a", 0), (float) warps.getDouble("warps", name + ".p", 0));
    }

    public static void removeWarp(String warpName) {
        String name = warpName.toLowerCase();
        warps.delete("warps", name + ".w");
        warps.delete("warps", name + ".x");
        warps.delete("warps", name + ".y");
        warps.delete("warps", name + ".z");
        warps.delete("warps", name + ".a");
        warps.delete("warps", name + ".p");
        warps.delete("warps", name + ".e");

        ArrayList<String> warpList = new ArrayList<>();
        if (warps.getString("warps", "warpList.array", 0) != null) {
            String listString = warps.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        }
        warpList.remove(warpName.toLowerCase());
        warps.set("warps", "warpList.array", warpList.toString());
    }

    public static void enableWarp(String warpName) {
        warps.set("warps", warpName.toLowerCase() + ".e", true + "");
    }

    public static void disableWarp(String warpName) {
        warps.set("warps", warpName.toLowerCase() + ".e", false + "");
    }

    public static ArrayList<String> getWarpList() {
        ArrayList<String> warpList = new ArrayList<>();
        if (warps.getString("warps", "warpList.array", 0) != null) {
            String listString = warps.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        }
        return warpList;
    }

    public static void sendWarpList(CommandSender s) {
        TextComponent list = new TextComponent("§aWarps§8: §f");
        for (String warp : getWarpList()) {
            if(ifWarpExists(warp)) {
                warp = warp + "§8, §f";
                TextComponent component = new TextComponent(warp);
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klicke zum Teleportieren").create()));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp teleport " + warp.replace("§8, §f", "")));
                list.addExtra(component);
            }
        }
        if (s instanceof Player p) {
            p.spigot().sendMessage(list);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
        } else {
            s.spigot().sendMessage(list);
        }
    }

    public static Location getCurrentSpawn(Player player) {
        try {
            if(ifWarpExists("spawn")&&isWarpEnabled("spawn")) {
                return getWarp("spawn");
            }
        } catch (Exception ignore) {}
        return Bukkit.getWorlds().getFirst().getSpawnLocation();
    }

    public static ArrayList<UUID> cooldown = new ArrayList<>();
    public static void startWarpCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if(!cooldown.contains(uuid)) {
            cooldown.add(uuid);
            new Countdown(600,Main.getInstance()) {
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