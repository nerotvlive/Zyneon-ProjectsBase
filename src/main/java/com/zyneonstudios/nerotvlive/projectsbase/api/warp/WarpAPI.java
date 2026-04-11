package com.zyneonstudios.nerotvlive.projectsbase.api.warp;

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

import java.util.*;

public class WarpAPI {

    private static HashMap<String, Warp> warps = new HashMap<>();
    private static Storage warpStorage;

    public static void initAPI() {
        if (Main.storage.getStorageType() == Storage.storageType.YAML) {
            warpStorage = new Storage("plugins/ProjectsBase/warps.yml");
        } else if (Main.storage.getStorageType() == Storage.storageType.SQLite) {
            warpStorage = new Storage("plugins/ProjectsBase/warps.db");
            warpStorage.setupTable("warps", 1);
        } else {
            warpStorage = Main.storage;
            warpStorage.setupTable("warps", 1);
        }
        initWarpList();
    }

    public static void setWarp(String warpName, World w, double x, double y, double z, float yaw, float pitch, boolean enable) {
        warpStorage.set("warps", warpName.toLowerCase() + ".w", w.getName());
        warpStorage.set("warps", warpName.toLowerCase() + ".x", x);
        warpStorage.set("warps", warpName.toLowerCase() + ".y", y);
        warpStorage.set("warps", warpName.toLowerCase() + ".z", z);
        warpStorage.set("warps", warpName.toLowerCase() + ".a", yaw);
        warpStorage.set("warps", warpName.toLowerCase() + ".p", pitch);
        warpStorage.set("warps", warpName.toLowerCase() + ".e", enable + "");

        Warp warp = new Warp(warpName, w, x, y, z, yaw, pitch);
        warp.setEnabled(enable);
        warps.put(warpName.toLowerCase(), warp);

        ArrayList<String> warpList = new ArrayList<>();
        if (warpStorage.getString("warps", "warpList.array", 0) != null) {
            String listString = warpStorage.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        }
        if (!warpList.contains(warpName.toLowerCase())) {
            warpList.add(warpName.toLowerCase());
        }
        warpStorage.set("warps", "warpList.array", warpList.toString());
    }

    public static void removeWarp(String warpName) {
        String name = warpName.toLowerCase();
        warpStorage.delete("warps", name + ".w");
        warpStorage.delete("warps", name + ".x");
        warpStorage.delete("warps", name + ".y");
        warpStorage.delete("warps", name + ".z");
        warpStorage.delete("warps", name + ".a");
        warpStorage.delete("warps", name + ".p");
        warpStorage.delete("warps", name + ".e");

        ArrayList<String> warpList = new ArrayList<>();
        if (warpStorage.getString("warps", "warpList.array", 0) != null) {
            String listString = warpStorage.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        }
        warpList.remove(warpName.toLowerCase());
        warpStorage.set("warps", "warpList.array", warpList.toString());
    }

    public static void setWarp(String Warpname, Player p, boolean enable) {
        Warpname = Warpname.toLowerCase();
        setWarp(Warpname, p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), enable);
    }

    @Deprecated
    public static void enableWarp(String warpName) {
        if(ifWarpExists(warpName)) {
            enableWarp(warps.get(warpName.toLowerCase()));
        }
    }

    @Deprecated
    public static void disableWarp(String warpName) {
        if(ifWarpExists(warpName)) {
            disableWarp(warps.get(warpName.toLowerCase()));
        }
    }

    public static void enableWarp(Warp warp) {
        String warpName = warp.getName().toLowerCase();
        warpStorage.set("warps", warpName + ".e", true + "");
    }

    public static void disableWarp(Warp warp) {
        String warpName = warp.getName().toLowerCase();
        warpStorage.set("warps", warpName + ".e", false + "");
    }

    public static boolean ifWarpExists(String warpName) {
        return warps.containsKey(warpName.toLowerCase());
    }

    @Deprecated
    public static boolean isWarpEnabled(String warpName) {
        if(ifWarpExists(warpName)) {
            return warps.get(warpName.toLowerCase()).isEnabled();
        }
        return false;
    }

    @Deprecated
    public static boolean isWarpEnabled(Warp warp) {
        return warp.isEnabled();
    }

    public static Collection<String> getWarpNames() {
        return warps.keySet();
    }

    public static Collection<Warp> getWarps() {
        return warps.values();
    }

    public static Warp getWarp(String warpName) {
        return warps.get(warpName.toLowerCase());
    }

    private static void initWarpList() {
        ArrayList<String> warpList = new ArrayList<>();
        if (warpStorage.getString("warps", "warpList.array", 0) != null) {
            String listString = warpStorage.getString("warps", "warpList.array", 0).toLowerCase().replace(" ", "").replace("[", "").replace("]", "");
            warpList = new ArrayList<>(Arrays.asList(listString.split(",")));
        }
        for(String warp : warpList) {
            Warp w = new Warp(warp.toLowerCase(), getWarpFromData(warp));
            w.setEnabled(warpStorage.getString("warps", warp.toLowerCase() + ".e", 0).equalsIgnoreCase("true"));
            warps.put(warp, w);
        }
    }

    private static Location getWarpFromData(String warpName) {
        String name = warpName.toLowerCase();
        return new Location(Bukkit.getWorld(warpStorage.getString("warps", name + ".w", 0)), warpStorage.getDouble("warps", name + ".x", 0), warpStorage.getDouble("warps", name + ".y", 0), warpStorage.getDouble("warps", name + ".z", 0), (float) warpStorage.getDouble("warps", name + ".a", 0), (float) warpStorage.getDouble("warps", name + ".p", 0));
    }

    public static Location getCurrentSpawn(Player player) {
        try {
            if(ifWarpExists("spawn")&&isWarpEnabled("spawn")) {
                return getWarp("spawn").getLocation();
            }
        } catch (Exception ignore) {}
        return Bukkit.getWorlds().getFirst().getSpawnLocation();
    }

    public static void sendWarpList(CommandSender s) {
        TextComponent list = new TextComponent("§aWarps§8: §f");
        for (String warp : getWarpNames()) {
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