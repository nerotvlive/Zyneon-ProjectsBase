package com.zyneonstudios.nerotvlive.projectsbase.custom;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.zyneonstudios.nerotvlive.projectsbase.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmorListener implements Listener {
    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = event.getNewItem();

        if (newItem != null && newItem.hasItemMeta()) {
            ItemMeta meta = newItem.getItemMeta();
            if (meta.hasItemModel()) {
                String model = meta.getItemModel().toString();
                if (model.equals("zyneon:sack")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, -1, 1, false, false));
                }
            }
        }

        ItemStack oldItem = event.getOldItem();
        if (oldItem != null && oldItem.hasItemMeta()) {
            ItemMeta meta = oldItem.getItemMeta();
            if (meta.hasItemModel()) {
                String model = meta.getItemModel().toString();
                if (model.equals("zyneon:sack")) {
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.getEquipment().getHelmet() != null){
            applyBlindnessIfSack(player);
        }
    }

    @EventHandler
    public void onDrinkingMilk(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack consumed = event.getItem();
        if(consumed.getType() == Material.MILK_BUCKET && player.getEquipment().getHelmet() != null){
            new BukkitRunnable(){
                @Override
                public void run() {
                    applyBlindnessIfSack(player);
                }
            }.runTaskLater(Main.getInstance(),1);

        }
    }

    private void applyBlindnessIfSack(Player player){
        ItemStack helmet = player.getEquipment().getHelmet();
        if(helmet.hasItemMeta()){
            ItemMeta meta = helmet.getItemMeta();
            if(meta.hasItemModel()){
                if(meta.getItemModel().toString().equals("zyneon:sack")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, -1, 1, false, false));
                }
            }
        }
    }
}