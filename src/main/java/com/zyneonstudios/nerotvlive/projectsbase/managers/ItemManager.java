package com.zyneonstudios.nerotvlive.projectsbase.managers;

import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemManager {

    public static ItemStack placeholder() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§0");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack close() {
        ItemStack item = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§cSchließen");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack spawn(Player player) {
        ItemStack item;
        if(!player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
            item = new ItemStack(Material.LIME_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eIn die Hauptwelt zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §aKostenlos");
            lore.add("§7Das kannst du dir leisten§8.");
            lore.add("§8----------------------");
            lore.add("§7Level (jetzt)§8: §e"+player.getLevel());
            lore.add("§7Level (danach)§8: §e"+player.getLevel());
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else {
            item = new ItemStack(Material.RED_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Stadtspawn zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §cUnendlich");
            lore.add("§7Das kannst du dir nicht leisten§8.");
            lore.add("§8----------------------");
            lore.add("§7Level (jetzt)§8: §e"+player.getLevel());
            lore.add("§7Level (danach)§8: §c-∞");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack farmworld(Player player) {
        ItemStack item;
        item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn die Farmwelt reisen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Kosten§8: §aKostenlos");
        lore.add("§7Das kannst du dir leisten§8.");
        lore.add("§8----------------------");
        lore.add("§7Level (jetzt)§8: §e" + player.getLevel());
        lore.add("§7Level (danach)§8: §e" + player.getLevel());
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack nether(Player player) {
        ItemStack item;
        item = new ItemStack(Material.NETHER_WART);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn den Nether reisen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Kosten§8: §aKostenlos");
        lore.add("§7Das kannst du dir leisten§8.");
        lore.add("§8----------------------");
        lore.add("§7Level (jetzt)§8: §e" + player.getLevel());
        lore.add("§7Level (danach)§8: §e" + player.getLevel());
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack end(Player player) {
        ItemStack item;
        item = new ItemStack(Material.ENDER_EYE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn das Ende reisen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Kosten§8: §aKostenlos");
        lore.add("§7Das kannst du dir leisten§8.");
        lore.add("§8----------------------");
        lore.add("§7Level (jetzt)§8: §e" + player.getLevel());
        lore.add("§7Level (danach)§8: §e" + player.getLevel());
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack deposit(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATE_SAND_PAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eEinzahlen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack payout(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATE_RED_SAND_PAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAuszahlen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack salary(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATEADDITION_DIAMOND_GRIT_SANDPAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eLohn abholen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterList(Player player) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eCharakter-Liste");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor(User user) {
        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAktuellen Charakter bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eName§8: §7"+user.getName());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_name(User user) {
        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eNamen bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eAktueller Name§8: §7"+user.getSelectedCharacter().getName());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_job(User user) {
        ItemStack itemStack = new ItemStack(Material.BREAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eJob bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eAktueller Job§8: §7"+user.getSelectedCharacter().getJob());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_skin(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAussehen bearbeiten §8(Skin URL)");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack backEditor() {
        ItemStack item = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§e<- Zurück");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack character_one(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§e"+user.getCharacters().getFirst().getName()+" (1) auswählen");
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack character_two(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§e"+user.getCharacters().get(1).getName()+" (2) auswählen");
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack character_three(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§e"+user.getCharacters().get(2).getName()+" (3) auswählen");
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
