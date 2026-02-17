package com.zyneonstudios.nerotvlive.projectsbase.managers;

import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
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

    public static ItemStack spawn(Player player) {
        ItemStack item;
        if(!player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
            item = new ItemStack(Material.LIME_TERRACOTTA);
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
        } else if(player.getLocation().distance(WarpAPI.getCurrentSpawn(player))<300) {
            item = new ItemStack(Material.RED_TERRACOTTA);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Silberfels-Hafen zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §c10 Level");
            lore.add("§7Du bist zu nah am Silberfels-Hafen für eine Schnellreise§8.");
            lore.add("§8----------------------");
            int level = player.getLevel()-10;
            lore.add("§7Level (jetzt)§8: §e"+player.getLevel());
            lore.add("§7Level (danach)§8: §e"+level);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else if(player.getLevel()>=10) {
            item = new ItemStack(Material.LIME_TERRACOTTA);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Silberfels-Hafen zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §a10 Level");
            lore.add("§7Das kannst du dir leisten§8.");
            lore.add("§8----------------------");
            int level = player.getLevel()-10;
            lore.add("§7Level (jetzt)§8: §e"+player.getLevel());
            lore.add("§7Level (danach)§8: §e"+level);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else {
            item = new ItemStack(Material.RED_TERRACOTTA);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Silberfels-Hafen zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §c10 Level");
            lore.add("§7Das kannst du dir nicht leisten§8.");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack farmworld(Player player) {
        ItemStack item;
        item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eZur Farmwelt reisen");
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
        lore.add("§eJob§8: §7"+user.getJob());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_name(User user) {
        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eNamen bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eAktueller Name§8: §7"+user.getName());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_job(User user) {
        ItemStack itemStack = new ItemStack(Material.BREAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eJob bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eAktueller Job§8: §7"+user.getJob());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_skin(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAussehen bearbeiten §8(Skin URL)");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eAktueller Skin§8: §7mache /char skin");
        lore.add("§eAktuelle Variante§8:§7 "+user.getSkinVariant());
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
        itemMeta.setDisplayName("§eCharakter 1 auswählen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eName§8:§7 "+user.getName(0));
        lore.add("§eJob§8:§7 "+user.getJob(0));
        lore.add("§eSkin§8: §7Wähle dazu den Charakter aus");
        lore.add("§eVariante§8:§7 "+user.getSkinVariant(0));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack character_two(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eCharakter 2 auswählen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eName§8:§7 "+user.getName(1));
        lore.add("§eJob§8:§7 "+user.getJob(1));
        lore.add("§eSkin§8: §7Wähle dazu den Charakter aus");
        lore.add("§eVariante§8:§7 "+user.getSkinVariant(1));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack character_three(User user) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eCharakter 3 auswählen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eName§8:§7 "+user.getName(2));
        lore.add("§eJob§8:§7 "+user.getJob(2));
        lore.add("§eSkin§8: §7Wähle dazu den Charakter aus");
        lore.add("§eVariante§8:§7 "+user.getSkinVariant(2));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
