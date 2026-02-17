package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.LockManager;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

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
    private boolean isRP = false;

    public User(UUID uuid) {
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
            lastLoc = WarpAPI.getWarp("spawn");
        }
        chatMode = "normal";
        inventoryMode = "normal";
        interactMode = "null";
        this.teamMode = false;
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

    public Location getLastLoc() {
        Location loc = lastLoc;
        if(lastLoc.add(0,1,0).getBlock().getType().toString().toLowerCase().contains("air")) {
            return loc;
        }
        return WarpAPI.getWarp("spawn");
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
        if(player !=null&& player.isOnline()) {
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

    public void sendRaw(String message) {
        if(getPlayer()!=null) {
            getPlayer().sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRaw(Strings.prefix+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(getPlayer()!=null) {
            getPlayer().playSound(getPlayer().getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void sendWarning(String warning) {
        sendRaw("§6[WICHTIG] §e"+warning.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(getPlayer()!=null) {
            getPlayer().playSound(getPlayer().getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
        }
    }

    public void sendError(String error) {
        sendRaw("§4[FEHLER] §c"+error.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(getPlayer()!=null) {
            getPlayer().playSound(getPlayer().getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public void sendDebug(String debug) {
        if(!Communicator.sendDebug) {
            return;
        }
        sendRaw("§9[DEBUG] §c"+debug.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(getPlayer()!=null) {
            getPlayer().playSound(getPlayer().getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
            getPlayer().playSound(getPlayer().getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
            getPlayer().playSound(getPlayer().getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void sendActionBar(String text) {
        if(getPlayer()!=null) {
            getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(text));
        }
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
        Main.onlineUsers.remove(uuid);
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
        System.gc();
    }
}