package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.managers.InventoryManager;
import com.zyneonstudios.nerotvlive.projectsbase.managers.ItemManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.Character;
import com.zyneonstudios.nerotvlive.projectsbase.objects.CharacterSkin;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings({"deprecation"})
public class InventoryClickListener implements Listener {

    SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
    SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
    PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem()!=null) {
            ItemStack item = e.getCurrentItem();
            if(item.getItemMeta()!=null) {
                ItemMeta itemMeta = item.getItemMeta();
                String itemName = itemMeta.getDisplayName();
                Player p = (Player)e.getWhoClicked();
                User u = Main.getUser(p);
                if(itemMeta.getLore()!=null) {
                    List<String> lore = itemMeta.getLore();
                    if (!lore.isEmpty()) {
                        if (lore.getFirst().startsWith("§8zyneon.characterEditor.")) {
                            e.setCancelled(true);
                            String number = lore.getFirst().replace("zyneon.characterEditor.", "").replace("§8","");
                            int index = Integer.parseInt(number);
                            u.setSelectedCharacter(index);
                            p.closeInventory();
                            p.openInventory(InventoryManager.characterEditor(u));
                            p.playSound(p, Sound.BLOCK_ENDER_CHEST_OPEN, 100, 100);
                            Communicator.sendInfo(p, "Du bist nun§8: §e" + u.getSelectedCharacter().getName());
                            Character character = u.getSelectedCharacter();
                            CharacterSkin skin = character.getSelectedSkin();
                            try {
                                Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin.getSkinUrl());
                                if (result.isPresent()) {
                                    playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
                                    skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
                                }
                            } catch (Exception ignore) {
                            }
                            u.initListName();
                            return;
                        }
                    }
                }
                if(itemName.equals(ItemManager.addCharacter.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.addNewCharacter();
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterList(u));
                } else if(itemName.equals(ItemManager.placeholder.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                } else if(itemName.equals(ItemManager.nether.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if(WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte kurz, bevor du dich teleportierst...");
                        return;
                    }
                    if(u.isGrounded()) {
                        if(p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                            Location silberfels = WarpAPI.getWarp("silberfels").getLocation();
                            Location rincon = WarpAPI.getWarp("rincon").getLocation();
                            int distance_silberfels = (int) silberfels.distance(p.getLocation());
                            int distance_rincon = (int) rincon.distance(p.getLocation());
                            if (distance_silberfels < distance_rincon) {
                                u.setLastCity("silberfels");
                            } else {
                                u.setLastCity("rincon");
                            }
                        }
                        p.closeInventory();
                        p.teleport(Objects.requireNonNull(Bukkit.getWorld("ne1")).getSpawnLocation());
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        WarpAPI.startWarpCooldown(p);
                        u.initListName();
                    } else {
                        Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                    }
                } else if(itemName.equals(ItemManager.end.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if(WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte kurz, bevor du dich teleportierst...");
                        return;
                    }
                    if(u.isGrounded()) {
                        if(p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                            Location silberfels = WarpAPI.getWarp("silberfels").getLocation();
                            Location rincon = WarpAPI.getWarp("rincon").getLocation();
                            int distance_silberfels = (int) silberfels.distance(p.getLocation());
                            int distance_rincon = (int) rincon.distance(p.getLocation());
                            if (distance_silberfels < distance_rincon) {
                                u.setLastCity("silberfels");
                            } else {
                                u.setLastCity("rincon");
                            }
                        }
                        p.closeInventory();
                        p.teleport(Objects.requireNonNull(Bukkit.getWorld("en1")).getSpawnLocation());
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        WarpAPI.startWarpCooldown(p);
                        u.initListName();
                    } else {
                        Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                    }
                } else if(itemName.equals(ItemManager.close.getItemMeta().getDisplayName())) {
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
                } else if(itemName.equals(ItemManager.farmworld.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if(WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte kurz, bevor du dich teleportierst...");
                        return;
                    }
                    if(u.isGrounded()) {
                        if(p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                            Location silberfels = WarpAPI.getWarp("silberfels").getLocation();
                            Location rincon = WarpAPI.getWarp("rincon").getLocation();
                            int distance_silberfels = (int) silberfels.distance(p.getLocation());
                            int distance_rincon = (int) rincon.distance(p.getLocation());
                            if (distance_silberfels < distance_rincon) {
                                u.setLastCity("silberfels");
                            } else {
                                u.setLastCity("rincon");
                            }
                        }
                        p.closeInventory();
                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(Strings.farmWorldName)).getSpawnLocation());
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        WarpAPI.startWarpCooldown(p);
                        u.initListName();
                    } else {
                        Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                    }
                } else if(itemName.equals(ItemManager.characterEditor_lite.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.openInventory(InventoryManager.characterEditor(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(itemName.equals(ItemManager.characterEditor_name(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_name");
                    Communicator.sendInfo(p,"Schreibe den neuen Namen von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(itemName.equals(ItemManager.characterEditor_job(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_job");
                    Communicator.sendInfo(p,"Schreibe den neuen Job von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(itemName.equals(ItemManager.backEditor.getItemMeta().getDisplayName())) {
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterHome_lite);
                    p.playSound(p,Sound.BLOCK_CHEST_OPEN,100,100);
                } else if(itemName.equals(ItemManager.characterEditor_skin().getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_variant");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-Variante §8(SLIM oder CLASSIC)§7 von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(itemName.equals(ItemManager.characterList_lite.getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.openInventory(InventoryManager.characterList(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(itemName.equals(ItemManager.spawn(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte kurz, bevor du dich teleportierst...");
                        return;
                    }
                    if (e.getCurrentItem().getType().toString().toLowerCase().contains("red")) {
                        Communicator.sendError(p, "§cDazu hast du nicht genügend Level§8!");
                        p.closeInventory();
                    } else {
                        if (u.isGrounded()) {
                            if (p.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                                p.setLevel(p.getLevel() - 10);
                            }
                            WarpAPI.startWarpCooldown(p);
                            p.closeInventory();
                            p.teleport(WarpAPI.getCurrentSpawn(u));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            u.initListName();
                        } else {
                            Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                        }
                    }
                }
            }
        }
    }
}