package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.managers.InventoryManager;
import com.zyneonstudios.nerotvlive.projectsbase.managers.ItemManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        User u = Main.getUser(p);
        if(e.getCurrentItem()!=null) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.placeholder().getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                } else if(u.getInventoryMode().equalsIgnoreCase("deposit")) {
                    if(e.getCurrentItem().getType().toString().toLowerCase().contains("zyneonsource_mark_")) {
                        e.setCancelled(true);
                        int amount = Integer.parseInt(e.getCurrentItem().getType().toString().replace("ZYNEONSOURCE_MARK_", ""));
                        amount = amount * e.getCurrentItem().getAmount();
                        e.getCurrentItem().setAmount(0);
                        u.addBalance(amount+0.0);
                        u.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§aDu hast "+amount+"§mM§r§a eingezahlt."));
                        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                    } else {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                    }
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.deposit(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setInventoryMode("deposit");
                    Communicator.sendInfo(p,"Klicke das Geld an§8,§7 welches zu einzahlen willst§8.");
                    p.openInventory(InventoryManager.bankerInventory_deposit(p));
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.payout(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setChatMode("payout");
                    Communicator.sendWarning(p,"Schreibe \"cancel\" in den Chat, um die Auszahlung abzubrechen.");
                    Communicator.sendInfo(p,"Schreibe den Betrag§8,§7 den du dir auszahlen lassen willst in den Chat§8.");
                    p.closeInventory();
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.salary(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.farmworld(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if(WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte etwas, bevor du dich erneut teleportierst...");
                        return;
                    }
                    if(u.isGrounded()) {
                        u.setLastLoc(p.getLocation());
                        p.closeInventory();
                        if(WarpAPI.isWarpEnabled("farmwelt")) {
                            p.teleport(WarpAPI.getWarp("farmwelt").getLocation());
                        } else {
                            p.teleport(Bukkit.getWorld(Strings.farmWorldName).getSpawnLocation());
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                    } else {
                        Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                    }
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.characterEditor(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.openInventory(InventoryManager.characterEditor(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.characterEditor_name(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_name");
                    Communicator.sendInfo(p,"Schreibe den neuen Namen von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.character_one(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setCharacter(0);
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterEditor(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.character_two(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setCharacter(1);
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterEditor(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.character_three(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setCharacter(2);
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterEditor(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.characterEditor_job(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_job");
                    Communicator.sendInfo(p,"Schreibe den neuen Job von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.backEditor().getItemMeta().getDisplayName())) {
                    p.closeInventory();
                    p.openInventory(InventoryManager.characterHome(u));
                    p.playSound(p,Sound.BLOCK_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.characterEditor_skin(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setChatMode("character_variant");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-Variante §8(SLIM oder CLASSIC)§7 von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.characterList(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.openInventory(InventoryManager.characterList(u));
                    p.playSound(p,Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.spawn(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (WarpAPI.cooldown.contains(p.getUniqueId())) {
                        Communicator.sendWarning(p,"Warte etwas, bevor du dich erneut teleportierst...");
                        return;
                    }
                    if (e.getCurrentItem().getType().toString().toLowerCase().contains("red")) {
                        Communicator.sendError(p, "§cDazu hast du nicht genügend Level§8!");
                        p.closeInventory();
                    } else {
                        if (u.isGrounded()) {
                            if (p.getWorld().equals(Bukkit.getWorlds().get(0))) {
                                p.setLevel(p.getLevel() - 10);
                            }
                            p.closeInventory();
                            p.teleport(WarpAPI.getCurrentSpawn(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        } else {
                            Communicator.sendError(p,"§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        User u = Main.getUser(e.getPlayer().getUniqueId());
        if(u.getInventoryMode().equalsIgnoreCase("deposit")) {
            Communicator.sendInfo(e.getPlayer(),"Einzahlen beendet§8.");
        }
        u.setInventoryMode("normal");
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if(!Main.getUser(e.getPlayer().getUniqueId()).getInventoryMode().equalsIgnoreCase("normal")) {
            e.setCancelled(true);
        }
    }
}