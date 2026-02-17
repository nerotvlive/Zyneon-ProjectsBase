package com.zyneonstudios.nerotvlive.projectsbase.managers;

import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static Inventory characterHome(User user) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Was willst du tun?");
        inventory.setItem(0,ItemManager.placeholder());
        inventory.setItem(1,ItemManager.characterEditor(user));
        inventory.setItem(2,ItemManager.placeholder());
        inventory.setItem(3,ItemManager.characterList(user.getPlayer()));
        inventory.setItem(4,ItemManager.placeholder());
        return inventory;
    }

    public static Inventory characterEditor(User user) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Charakter Editor");
        inventory.setItem(0,ItemManager.characterEditor_name(user));
        inventory.setItem(2,ItemManager.characterEditor_skin(user));
        inventory.setItem(1,ItemManager.characterEditor_job(user));
        inventory.setItem(3,ItemManager.placeholder());
        inventory.setItem(4,ItemManager.backEditor());
        return inventory;
    }

    public static Inventory characterList(User user) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Charakterliste");
        inventory.setItem(0,ItemManager.character_one(user));
        inventory.setItem(1,ItemManager.character_two(user));
        inventory.setItem(2,ItemManager.character_three(user));
        inventory.setItem(3,ItemManager.placeholder());
        inventory.setItem(4,ItemManager.backEditor());
        return inventory;
    }

    public static Inventory spawnInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Warpmenü");
        inventory.setItem(0, ItemManager.placeholder());
        inventory.setItem(1, ItemManager.spawn(player));
        inventory.setItem(2, ItemManager.placeholder());
        inventory.setItem(3, ItemManager.farmworld(player));
        inventory.setItem(4, ItemManager.placeholder());
        return inventory;
    }

    public static Inventory bankerInventory_home(Player player) {
        Inventory inventory = Bukkit.createInventory(null,InventoryType.HOPPER,"Bänker");
            inventory.setItem(0, ItemManager.deposit(player));
            inventory.setItem(1, ItemManager.placeholder());
            inventory.setItem(2, ItemManager.salary(player));
            inventory.setItem(3, ItemManager.placeholder());
            inventory.setItem(4, ItemManager.payout(player));
        return inventory;
    }

    public static Inventory bankerInventory_deposit(Player player) {
        return Bukkit.createInventory(null,InventoryType.HOPPER,"Geld einzahlen");
    }
}