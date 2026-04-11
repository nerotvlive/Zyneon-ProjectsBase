package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.LockManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserAlt {

    private UUID uuid;
    private OfflinePlayer offlinePlayer;
    private Player player = null; //DEB
    private String inventoryMode;
    private String interactMode;
    private String chatMode;
    private boolean teamMode;
    private String skinURL;
    private String skinVariant;
    private String job;
    private String name;
    private int character;
    private Location lastLoc;
    private String lastCity;
    private boolean isRP = false;

    public UserAlt(UUID uuid) {
        this.uuid = uuid;
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(Bukkit.getPlayer(uuid)!=null) {
            this.player = Bukkit.getPlayer(uuid);
        }
        if(!Main.economy.hasBalance(uuid)) {
            Main.economy.setBalance(uuid, 0);
        }
        if(!Main.economy.hasSalary(uuid)) {
            Main.economy.setSalary(uuid, 0);
        }
        if(Main.storage.get("profiles",uuid+"_char",0)!=null) {
            character = Main.storage.getInteger("profiles",uuid+"_char",0);
        } else {
            character = 0;
        }
        if(Main.storage.get("profiles",uuid+"_lastLoc",0)!=null) {
            lastLoc = LockManager.decryptLocation(Main.storage.getString("profiles",uuid+"_lastLoc",0));
        } else {
            lastLoc = Bukkit.getWorlds().getFirst().getSpawnLocation();
        }
        chatMode = "normal";
        inventoryMode = "normal";
        interactMode = "null";
        this.teamMode = false;
        try {
            this.lastCity = Main.storage.get("users." + uuid, "city", 0).toString();
        } catch (Exception e) {
            setLastCity(Math.random() < 0.5 ? "silberfels" : "rincon");
        }
    }

    public void setupCharacter(int c) {
        name = "Unbekannt";
        job = "Arbeitslos";
        skinURL = null;
        skinVariant = null;
        if(Main.storage.get("characters"+c,uuid+"_job",0)!=null) {
            job = Main.storage.getString("characters"+c,uuid+"_job",0);
        }
        if(Main.storage.get("characters"+c,uuid+"_name",0)!=null) {
            name = Main.storage.getString("characters"+c,uuid+"_name",0);
        }
        if(Main.storage.get("characters"+c,uuid+"_skinVariant",0)!=null) {
            skinVariant = Main.storage.getString("characters"+c,uuid+"_skinVariant",0);
        }
    }

    public void setLastLoc(Location lastLoc) {
        if(lastLoc.getWorld().equals(Bukkit.getWorlds().get(0))) {
            Main.storage.setString("profiles", uuid + "_lastLoc", LockManager.encryptLocation(lastLoc), 0);
            this.lastLoc = lastLoc;
        }
    }

    public void setLastCity(String lastCity) {
        Main.storage.set("users."+uuid,"city",lastCity,0);
        this.lastCity = lastCity;
    }

    public Location getLastLoc() {
        Location loc = lastLoc;
        if(lastLoc.add(0,1,0).getBlock().getType().toString().toLowerCase().contains("air")) {
            return loc;
        }
        try {
            return WarpAPI.getCurrentSpawn(player);
        } catch (Exception e) {
            return Bukkit.getWorlds().getFirst().getSpawnLocation();
        }
    }

    public String getLastCity() {
        return lastCity;
    }

    public void setName(String name) {
        Main.storage.setString("characters"+character,uuid+"_name",name,0);
        this.name = name;
    }

    public void setJob(String job) {
        Main.storage.setString("characters"+character,uuid+"_job",job,0);
        this.job = job;
    }

    public void setSkinVariant(String skinVariant) {
        Main.storage.setString("characters"+character,uuid+"_skinVariant",skinVariant,0);
        this.skinVariant = skinVariant;
    }

    public void setCharacter(int character) {
        this.character = character;
        Main.storage.set("profiles",uuid+"_char",character,0);
        setupCharacter(character);
    }

    public void setJoined(boolean joined) {
        Main.storage.set("users."+uuid,"joined",joined,0);
    }

    public int getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getSkinURL() {
        return skinURL;
    }

    public String getSkinVariant() {
        return skinVariant;
    }

    public String getName(int c) {
        if(Main.storage.get("characters"+c,uuid+"_name",0)!=null) {
            return Main.storage.getString("characters"+c,uuid+"_name",0);
        } else {
            return null;
        }
    }

    public String getJob(int c) {
        if(Main.storage.get("characters"+c,uuid+"_job",0)!=null) {
            return Main.storage.getString("characters"+c,uuid+"_job",0);
        } else {
            return null;
        }
    }

    public String getSkinURL(int c) {
        if(Main.storage.get("characters"+c,uuid+"_skinURL",0)!=null) {
            return Main.storage.getString("characters"+c,uuid+"_skinURL",0);
        } else {
            return null;
        }
    }

    public String getSkinVariant(int c) {
        if(Main.storage.get("characters"+c,uuid+"_skinVariant",0)!=null) {
            return Main.storage.getString("characters"+c,uuid+"_skinVariant",0);
        } else {
            return null;
        }
    }

    public boolean getJoined() {
        return Main.storage.get("users."+uuid, "joined", 0) != null;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    public Double getBalance() {
        return Main.economy.getBalance(uuid);
    }

    public Double getSalary() {
        return Main.economy.getSalary(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Player getPlayer() {
        if(player !=null) {
            return player;
        } else {
            return Bukkit.getPlayer(uuid);
        }
    }

    public int getPing() {
        if(getPlayer()!=null) {
            String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if (!getPlayer().getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
                player = Bukkit.getPlayer(getPlayer().getUniqueId());
            }
            try {
                assert getPlayer() != null;
                return getPlayer().getPing();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        return -1;
    }

    public String getInventoryMode() {
        return inventoryMode;
    }

    public String getInteractMode() {
        return this.interactMode;
    }

    public String getChatMode() {
        return chatMode;
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

    public void setBalance(Double balance) {
        Main.economy.setBalance(uuid,balance);
    }

    public void addBalance(Double amount) {
        setBalance(getBalance()+amount);
    }

    public void removeBalance(Double amount) {
        setBalance(getBalance()-amount);
    }

    public void setSalary(Double salary) {
        Main.economy.setSalary(uuid,salary);
    }

    public void addSalary(Double amount) {
        setSalary(getSalary()+amount);
    }

    public void removeSalary(Double amount) {
        setSalary(getSalary()-amount);
    }

    public void setInventoryMode(String inventoryMode) {
        this.inventoryMode = inventoryMode;
    }

    public void setInteractMode(String interactMode) {
        if(this.interactMode.contains("mode")) {
            if(interactMode.contains("nullmode")) {
                this.interactMode = "null";
                return;
            }
            return;
        }
        this.interactMode = interactMode;
    }

    public void setChatMode(String chatMode) {
        this.chatMode = chatMode;
    }

    public boolean isRP() {
        return isRP;
    }

    public void setRP(boolean RP) {
        isRP = RP;
    }

    public boolean toggleRP() {
        isRP = !isRP;
        return isRP;
    }

    public void destroy() {
        this.uuid = null;
        this.offlinePlayer = null;
        this.interactMode = null;
        this.inventoryMode = null;
        this.chatMode = null;
        this.teamMode = false;
        this.skinURL = null;
        this.skinVariant = null;
        this.job = null;
        this.name = null;
        this.character = -1;
        this.player = null;
    }
}