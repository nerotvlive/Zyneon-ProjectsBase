package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.commands.SRLCommand;
import com.zyneonstudios.nerotvlive.projectsbase.objects.Character;
import com.zyneonstudios.nerotvlive.projectsbase.objects.CharacterSkin;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.Optional;

public class PlayerJoinListener implements Listener {

    SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
    SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
    PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setScoreboard(Main.getScoreboard());
        Main.getScoreboard().getTeam("rp").addPlayer(p);
        Main.onlineUsers.remove(p.getUniqueId());
        User u = Main.getUser(p);

        p.setPlayerListHeader("\n§cPrimal 4\n§cPrimal Aftermath\n");
        p.setPlayerListFooter("\n§7by §fZYNEON PROJECTS\n§7from ZYNEON STUDIOS\n");

        if(!p.hasPlayedBefore()) {
            p.teleport(getRandomSpawn());
        }
        Character character = u.getSelectedCharacter();
        p.setPlayerListName(p.getScoreboard().getTeam("rp").getPrefix()+character.getName());
        CharacterSkin skin = character.getSelectedSkin();

        try {
            Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin.getSkinUrl());
            if(result.isPresent()) {
                playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
                skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        e.setJoinMessage("§8» §a"+p.getName());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(SRLCommand.countdown!=null) {
            e.setKickMessage("§cDer Server startet zurzeit neu§8,§c versuch es später erneut§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(Main.maintenance) {
            if(!e.getPlayer().hasPermission("zyneon.team")) {
                e.setKickMessage("§cDer Server ist zurzeit im Wartungsmodus§8,§c versuch es später erneut§8!");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            }
        } else if(!Main.whitelist.contains(e.getPlayer().getUniqueId().toString())) {
            e.setKickMessage("§cDu darfst auf diesem Server nicht spielen§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    private static ArrayList<String> warps = null;
    private static void initSpawns() {
        warps = new ArrayList<>();
        for(int i = 1; i <= 30; i++) {
            String name = "silberfelshotel"+i;
            if(WarpAPI.ifWarpExists(name)) {
                warps.add(name);
            }
        }
        for(int i = 1; i <= 16; i++) {
            String name = "rinconhotel"+i;
            if(WarpAPI.ifWarpExists(name)) {
                warps.add(name);
            }
        }
    }

    private static Location getRandomSpawn() {
        try {
            if (warps == null) {
                initSpawns();
            }
            String result = "silberfelshotel";
            int max = 30;
            boolean rincon = Math.random() < 0.5;
            if (rincon) {
                result = "rinconhotel";
                max = 16;
            }
            for (int i = 1; i <= max; i++) {
                String warpName = result + i;
                if (warps.contains(warpName)) {
                    return WarpAPI.getWarp(warpName).getLocation();
                }
            }
            if (rincon) {
                result = "silberfelshotel";
                max = 30;
            } else {
                result = "rinconhotel";
                max = 16;
            }
            for (int i = 1; i <= max; i++) {
                String warpName = result + i;
                if (warps.contains(warpName)) {
                    return WarpAPI.getWarp(warpName).getLocation();
                }
            }
            if (rincon) {
                return WarpAPI.getWarp("rincon").getLocation();
            } else {
                return WarpAPI.getWarp("silberfels").getLocation();
            }
        } catch (Exception e) {
            Communicator.sendError(e.getMessage());
            return Bukkit.getWorlds().getFirst().getSpawnLocation();
        }
    }
}