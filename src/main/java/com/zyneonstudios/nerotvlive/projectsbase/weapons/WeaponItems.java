package com.zyneonstudios.nerotvlive.projectsbase.weapons;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class WeaponItems {

    private static HashMap<String, ItemStack> weapons = new HashMap<>();

    public static Inventory getWeaponsMenu() {
        Inventory inventory = Bukkit.createInventory(null,18*3, "Weapons");
        for(String key : weapons.keySet()) {
            inventory.addItem(weapons.get(key).clone());
        }
        return inventory;
    }

    public static void init() {
        weapons.clear();
        weapons.put("revolver",getRevolver(Variant.SILVER));
        weapons.put("black_revolver",getRevolver(Variant.BLACK));
        weapons.put("golden_revolver",getRevolver(Variant.GOLDEN));

        weapons.put("shotgun",getShotgun(Variant.SILVER));
        weapons.put("black_shotgun",getShotgun(Variant.BLACK));
        weapons.put("golden_shotgun",getShotgun(Variant.GOLDEN));

        weapons.put("rifle",getRifle(Variant.SILVER));
        weapons.put("black_rifle",getRifle(Variant.BLACK));
        weapons.put("golden_rifle",getRifle(Variant.GOLDEN));

        weapons.put("mauser",getMauser(Variant.SILVER));
        weapons.put("black_mauser",getMauser(Variant.BLACK));
        weapons.put("golden_mauser",getMauser(Variant.GOLDEN));

        weapons.put("sniper_rifle",getSniperRifle(Variant.SILVER));
        weapons.put("black_sniper_rifle",getSniperRifle(Variant.BLACK));
        weapons.put("golden_sniper_rifle",getSniperRifle(Variant.GOLDEN));

        weapons.put("luger",getLuger(Variant.SILVER));
        weapons.put("black_luger",getLuger(Variant.BLACK));
        weapons.put("golden_luger",getLuger(Variant.GOLDEN));

        weapons.put("lewis",getLewis(Variant.SILVER));
        weapons.put("black_lewis",getLewis(Variant.BLACK));
        weapons.put("golden_lewis",getLewis(Variant.GOLDEN));

        weapons.put("marksman_pistol",getMarksmanPistol(Variant.SILVER));
        weapons.put("black_marksman_pistol",getMarksmanPistol(Variant.BLACK));
        weapons.put("golden_marksman_pistol",getMarksmanPistol(Variant.GOLDEN));

        weapons.put("knife",getKnife());
        weapons.put("hammer",getHammer());
        weapons.put("dagger",getDagger());
        weapons.put("baton",getBaton());
    }

    private static ItemStack getFormattedCrossbow() {
        ItemStack crossbow = new ItemStack(Material.CROSSBOW,1);
        CrossbowMeta meta = (CrossbowMeta)crossbow.getItemMeta();
        meta.setDisplayName("§r§fSchusswaffe");
        crossbow.setItemMeta(meta);
        return crossbow;
    }

    public static ItemStack getRevolver(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fRevolver");
        meta.setItemModel(new NamespacedKey("zyneon", "revolver_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getSniperRifle(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fScharfschützengewehr");
        meta.setItemModel(new NamespacedKey("zyneon", "sniper_rifle_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getShotgun(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fSchrotflinte");
        meta.setItemModel(new NamespacedKey("zyneon", "shotgun_"+variant.name().toLowerCase()));
        meta.addEnchant(Enchantment.MULTISHOT,1,true);
        meta.setEnchantmentGlintOverride(false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getRifle(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fGewehr");
        meta.setItemModel(new NamespacedKey("zyneon", "rifle_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getMauser(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fMauser C96");
        meta.setItemModel(new NamespacedKey("zyneon", "mauser_c96_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getMarksmanPistol(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fPräzisionspistole");
        meta.setItemModel(new NamespacedKey("zyneon", "marksman_pistol_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getLuger(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fLuger");
        meta.setItemModel(new NamespacedKey("zyneon", "luger_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getLewis(Variant variant) {
        ItemStack revolver = getFormattedCrossbow();
        CrossbowMeta meta = (CrossbowMeta)revolver.getItemMeta();

        meta.setDisplayName("§r§fLewis Maschinengewehr");
        meta.setItemModel(new NamespacedKey("zyneon", "lewis_gun_"+variant.name().toLowerCase()));

        revolver.setItemMeta(meta);
        return revolver;
    }

    public static ItemStack getKnife() {
        ItemStack knife = new ItemStack(Material.STONE_SWORD);
        ItemMeta meta = knife.getItemMeta();

        meta.setDisplayName("§r§fMesser");
        meta.setItemModel(new NamespacedKey("zyneon", "knife"));
        AttributeModifier speedModifier = new AttributeModifier(
                new NamespacedKey(Main.getInstance(), "knifeAttackSpeed"),
                2.0,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
        );
        meta.addAttributeModifier(Attribute.ATTACK_SPEED, speedModifier);

        knife.setItemMeta(meta);
        return knife;
    }

    public static ItemStack getHammer() {
        ItemStack hammer = new ItemStack(Material.STONE_AXE);
        ItemMeta meta = hammer.getItemMeta();

        meta.setDisplayName("§r§fHammer");
        meta.setItemModel(new NamespacedKey("zyneon", "hammer"));

        hammer.setItemMeta(meta);
        return hammer;
    }

    public static ItemStack getDagger() {
        ItemStack hammer = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = hammer.getItemMeta();

        meta.setUseCooldown(null);
        meta.setDisplayName("§r§fDolch");
        meta.setItemModel(new NamespacedKey("zyneon", "hammer"));

        hammer.setItemMeta(meta);
        return hammer;
    }

    public static ItemStack getBaton() {
        ItemStack hammer = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = hammer.getItemMeta();

        meta.setUseCooldown(null);
        meta.setDisplayName("§r§fKnüppel");
        meta.setItemModel(new NamespacedKey("zyneon", "baton"));

        hammer.setItemMeta(meta);
        return hammer;
    }

    public enum Variant {
        SILVER,
        GOLDEN,
        BLACK,
    }

    public static HashMap<String, ItemStack> getWeapons() {
        return weapons;
    }
}