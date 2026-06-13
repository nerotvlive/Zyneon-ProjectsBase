package com.zyneonstudios.nerotvlive.projectsbase.managers;

import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

//I still decide myself if I want this component bullshit or not - No need for it, when Strings just work fine.
@SuppressWarnings("deprecation")
public class ItemManager {

    public static ItemStack placeholder = placeholder();
    private static ItemStack placeholder() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§0");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack close = close();
    private static ItemStack close() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§cSchließen");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack farmworld = farmworld();
    private static ItemStack farmworld() {
        ItemStack item;
        item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn die Farmwelt reisen");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack nether = nether();
    private static ItemStack nether() {
        ItemStack item;
        item = new ItemStack(Material.NETHER_WART);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn den Nether reisen");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack end = end();
    private static ItemStack end() {
        ItemStack item;
        item = new ItemStack(Material.ENDER_EYE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eIn das Ende reisen");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack characterList_lite = characterList_lite();
    private static ItemStack characterList_lite() {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eCharakter-Liste");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack characterEditor_lite = characterEditor_lite();
    private static ItemStack characterEditor_lite() {
        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAktuellen Charakter bearbeiten");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack backEditor = backEditor();
    private static ItemStack backEditor() {
        ItemStack item = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§e<- Zurück");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack addCharacter = addCharacter();
    private static ItemStack addCharacter() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eNeuen Charakter erstellen");
        itemMeta.setLore(new ArrayList<>());
        item.setItemMeta(itemMeta);
        return item;
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

    public static ItemStack characterEditor_skin() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAussehen bearbeiten §8(Skin URL)");
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack character(User user, int number) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§e"+user.getCharacters().get(number).getName()+" auswählen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§8zyneon.characterEditor."+number);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack spawn(Player player) {
        ItemStack item;
        if(!player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
            item = new ItemStack(Material.LIME_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eIn die Hauptwelt zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else {
            item = new ItemStack(Material.RED_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Stadtspawn zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§cDas darfst du hier nicht!");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }
        return item;
    }
}
