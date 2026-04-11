package com.zyneonstudios.nerotvlive.projectsbase.commands;

import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.property.SkinVariant;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URL;
import java.util.Optional;

public class SkinCommand implements CommandExecutor {

    private SkinsRestorer skinsRestorerAPI;

    public SkinCommand() {
        this.skinsRestorerAPI = SkinsRestorerProvider.get();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender.isOp()) {
            if(args.length == 0) {
                sender.sendMessage("Usage: /skin <url> [NORMAL/slim/N/s]");
                return true;
            }
            if(sender instanceof Player p) {
                String url = "";
                try {
                    URL validate = URI.create(args[0]).toURL();
                    url = validate.toString();
                } catch (Exception e) {
                    sender.sendMessage("Fehlerhafte URL!");
                    return true;
                }
                SkinVariant type = SkinVariant.CLASSIC;
                if(args.length > 1) {
                    if(args[1].equalsIgnoreCase("slim") || args[1].equalsIgnoreCase("S")) {
                        type = SkinVariant.SLIM;
                    }
                }
                p.sendMessage("Lade Skin mit folgenden Daten:");
                p.sendMessage("URL: " + url);
                p.sendMessage("Type: " + type);

                SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
                PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

                try {
                    Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(url,type);
                    if(result.isPresent()) {
                        playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
                    } else {
                        p.sendMessage("Fehler beim Laden des Skins! (Result nicht vorhanden)");
                    }
                    skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
                } catch (Exception e) {
                    p.sendMessage("Fehler beim Laden des Skins!");
                    p.sendMessage(e.getMessage());
                    p.sendMessage(e.getStackTrace()[1].toString());
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }
}
