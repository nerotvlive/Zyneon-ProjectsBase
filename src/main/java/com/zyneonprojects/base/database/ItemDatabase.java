package com.zyneonprojects.base.database;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemDatabase {

    private static ArrayList<String> items = new ArrayList<>();

    public static void initDatabase() {
        items.clear();
        for (Material material : Material.values()) {
            String name = material.name().toLowerCase();
            items.add(name);
            items.add("minecraft:" + name);
        }
    }

    public static ItemStack get(Material material) {
        return new ItemStack(material, 1);
    }

    public static ItemStack get(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack get(String id) {
        try {
            return new ItemStack(Material.valueOf(id.replace("minecraft:", "").toUpperCase()), 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static ItemStack get(String id, int amount) {
        try {
            return new ItemStack(Material.valueOf(id.replace("minecraft:", "").toUpperCase()), amount);
        } catch (Exception e) {
            return null;
        }
    }
}
