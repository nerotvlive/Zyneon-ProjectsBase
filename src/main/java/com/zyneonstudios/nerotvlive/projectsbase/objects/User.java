package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final Config config;

    private final UUID uuid;
    private final OfflinePlayer offlinePlayer;
    private final String name;
    private Player player = null;
    private boolean roleplay = true;
    private String lastCity = Math.random() < 0.5 ? "silberfels" : "rincon";

    //MODES
    private String inventoryMode;
    private String interactMode;
    private String chatMode;
    private boolean teamMode;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.config = new Config("plugins/ProjectsBase/users/" + uuid.toString() + ".yml");
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (Bukkit.getPlayer(uuid) != null) {
            this.player = Bukkit.getPlayer(uuid);
            this.name = player.getName();
        } else {
            this.name = offlinePlayer.getName();
        }
    }

    public String getName() {
        return name;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isRoleplay() {
        return roleplay;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setChatMode(String chatMode) {
        this.chatMode = chatMode;
    }

    public void setInteractMode(String interactMode) {
        this.interactMode = interactMode;
    }

    public void setInventoryMode(String inventoryMode) {
        this.inventoryMode = inventoryMode;
    }

    public void setPlayer(Player player) {
        if(player.getUniqueId().equals(this.uuid)) {
            this.player = player;
        }
    }

    public void setRoleplay(boolean roleplay) {
        this.roleplay = roleplay;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    public Config getConfig() {
        return config;
    }

    public String getChatMode() {
        return chatMode;
    }

    public String getInteractMode() {
        return interactMode;
    }

    public String getInventoryMode() {
        return inventoryMode;
    }

    public String getLastCity() {
        return lastCity;
    }

    private boolean isGroundedCheck() {
        if (getPlayer() != null) {
            Player p = getPlayer();
            if (p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("slab") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("stair") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("farmland") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("path")) {
                return false;
            } else if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.AIR)) {
                if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.VOID_AIR)) {
                    return p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.CAVE_AIR);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean isGrounded() {
        return !isGroundedCheck();
    }
}