package com.zyneonstudios.nerotvlive.projectsbase.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class CustomItems {
    private static HashMap<String, ItemStack> customItems = new HashMap<>();

    public static Inventory getCustomItemsMenu() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Custom Items");
        for (String key : customItems.keySet()) {
            inventory.addItem(customItems.get(key).clone());
        }
        return inventory;
    }

    public static void init() {
        customItems.clear();
        customItems.put("mark_1", getMark(Variant.ONE));
        customItems.put("mark_2", getMark(Variant.TWO));
        customItems.put("mark_5", getMark(Variant.FIVE));
        customItems.put("mark_10", getMark(Variant.TEN));
        customItems.put("mark_20", getMark(Variant.TWENTY));
        customItems.put("mark_50", getMark(Variant.FIFTY));
        customItems.put("mark_100", getMark(Variant.ONEHUNDRET));
        customItems.put("mark_200", getMark(Variant.TWOHUNDRET));

        customItems.put("ice", getIce());
        customItems.put("fishbasket", getFishbasket());
        customItems.put("wrench", getWrench());
        customItems.put("toolbox", getToolbox());
    }

    public static ItemStack getMark(Variant variant) {
        ItemStack mark;
        if (variant.numValue < 5) {
            mark = new ItemStack(Material.COPPER_NUGGET);
        } else {
            mark = new ItemStack(Material.PAPER);
        }
        ItemMeta meta = mark.getItemMeta();
        meta.setDisplayName("§r§f" + variant.numValue + " Mark");
        meta.setItemModel(new NamespacedKey("zyneon", "mark" + variant.numValue));
        mark.setItemMeta(meta);
        return mark;
    }

    public static ItemStack getIce() {
        ItemStack ice = new ItemStack(Material.COOKIE);
        ItemMeta meta = ice.getItemMeta();
        meta.setDisplayName("§r§fEis");
        meta.setItemModel(new NamespacedKey("zyneon", "ice"));
        ice.setItemMeta(meta);
        return ice;
    }

    public static ItemStack getFishbasket() {
        ItemStack fishbasket = new ItemStack(Material.COOKED_BEEF);
        ItemMeta meta = fishbasket.getItemMeta();
        meta.setDisplayName("§r§fFischbrötchen");
        meta.setItemModel(new NamespacedKey("zyneon", "fishbasket"));
        fishbasket.setItemMeta(meta);
        return fishbasket;
    }

    public static ItemStack getWrench(){
        ItemStack wrench = new ItemStack(Material.DEBUG_STICK);
        ItemMeta meta = wrench.getItemMeta();
        meta.setDisplayName("§r§fSchraubenschlüssel");
        meta.setEnchantmentGlintOverride(false);
        wrench.setItemMeta(meta);
        return wrench;
    }

    public static ItemStack getToolbox(){
        ItemStack toolbox = new ItemStack(Material.STICK);
        ItemMeta meta = toolbox.getItemMeta();
        meta.setDisplayName("§r§fWerkzeugkasten");
        meta.setItemModel(new NamespacedKey("zyneon", "toolbox"));
        toolbox.setItemMeta(meta);
        return toolbox;
    }

    public enum Variant {
        ONE(1),
        TWO(2),
        FIVE(5),
        TEN(10),
        TWENTY(20),
        FIFTY(50),
        ONEHUNDRET(100),
        TWOHUNDRET(200);

        private int numValue;

        Variant(int numValue) {
            this.numValue = numValue;
        }

        public int getNumValue() {
            return numValue;
        }
    }

    public static HashMap<String, ItemStack> getCustomItems() {
        return customItems;
    }
}
