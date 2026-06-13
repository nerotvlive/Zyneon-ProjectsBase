package com.zyneonstudios.nerotvlive.projectsbase.managers;

import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("deprecation")
public class InventoryManager {

    final public static Inventory characterHome_lite = characterHome_lite();
    private static Inventory characterHome_lite() {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Charaktereinstellungen");
        inventory.setItem(0,ItemManager.placeholder);
        inventory.setItem(1,ItemManager.characterEditor_lite);
        inventory.setItem(2,ItemManager.placeholder);
        inventory.setItem(3,ItemManager.characterList_lite);
        inventory.setItem(4,ItemManager.placeholder);
        return inventory;
    }

    public static Inventory characterEditor(User user) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Charakter Editor");
        inventory.setItem(0,ItemManager.characterEditor_name(user));
        inventory.setItem(1,ItemManager.characterEditor_skin());
        inventory.setItem(2,ItemManager.characterEditor_job(user));
        inventory.setItem(3,ItemManager.placeholder);
        inventory.setItem(4,ItemManager.backEditor);
        return inventory;
    }

    public static Inventory characterList(User user) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Charakterliste");
        for(int i = 0; i < user.getCharacters().size(); i++) {
            if(i>53) break;
            inventory.setItem(i,ItemManager.character(user,i));
        }
        inventory.setItem(52,ItemManager.addCharacter);
        inventory.setItem(53,ItemManager.backEditor);
        return inventory;
    }

    public static Inventory spawnInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Warpmenü");
        inventory.setItem(0, ItemManager.spawn(player));
        inventory.setItem(1, ItemManager.farmworld);
        inventory.setItem(2, ItemManager.nether);
        inventory.setItem(3, ItemManager.end);
        inventory.setItem(4, ItemManager.close);
        return inventory;
    }
}