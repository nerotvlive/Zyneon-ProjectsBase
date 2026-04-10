package com.zyneonstudios.nerotvlive.projectsbase.weapons;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import java.util.List;
import java.awt.*;

public class WeaponListener implements Listener {

    private final NamespacedKey ammoKey;

    public WeaponListener() {
        ammoKey = new NamespacedKey(Main.getInstance(), "ammo");
    }

    @EventHandler
    public void onWeaponShot(EntityShootBowEvent event){
        if(event.getEntity() instanceof Player player){
            ItemStack weapon = event.getBow();
            if(weapon != null && weapon.getType() == Material.CROSSBOW){
                var meta = weapon.getItemMeta();

                if(!meta.hasItemModel()){
                    player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_shoot", 1f,1f);
                }
                else if(meta.hasItemModel()){
                    String weaponModel = meta.getItemModel().toString();
                    double damage = 1.0;
                    double velocity = 1.0;

                    switch (weaponModel){
                        case String s when s.startsWith("zyneon:revolver"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.revolver_shoot",2f,1f);
                            damage = 1.5;
                            break;
                        case String s when s.startsWith("zyneon:shotgun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.shotgun_shoot",2f,1f);
                            damage = 1.5;
                            break;
                        case String s when s.startsWith("zyneon:marksman_pistol"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.marksman_pistol_shoot",2f,1f);
                            damage = 2.5;
                            break;
                        case String s when s.startsWith("zyneon:mauser_c96"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.mauser_c96_shoot",2f,1f);
                            AutomaticReload(player, weapon,8);
                            damage = 0.7;
                            break;
                        case String s when s.startsWith("zyneon:lewis_gun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.lewis_gun_shoot",2f,1f);
                            AutomaticReload(player, weapon,48);
                            damage = 0.5;
                            break;
                        case String s when s.startsWith("zyneon:luger"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.luger_shoot",2f,1f);
                            AutomaticReload(player, weapon,8);
                            damage = 0.7;
                            break;
                        case String s when s.startsWith("zyneon:rifle"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.rifle_shoot",2f,1f);
                            velocity = 2.0;
                            damage = 3.0;
                            break;
                        default:
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_shoot", 1f,1f);
                            break;
                    }

                    Entity projectile = event.getProjectile();
                    if (projectile instanceof AbstractArrow arrow) {
                        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                        arrow.setSilent(true);
                        arrow.setVelocity(arrow.getVelocity().multiply(velocity));
                        arrow.setDamage(damage);

                        for (Player all : org.bukkit.Bukkit.getOnlinePlayers()) {
                            all.hideEntity(Main.getInstance(), arrow);
                        }

                        new BukkitRunnable(){
                            Location lastPos = arrow.getLocation();

                            @Override
                            public void run(){
                                if(arrow.isOnGround()){
                                    arrow.remove();
                                    this.cancel();
                                    return;
                                }
                                if (arrow.isDead() || !arrow.isValid()) {
                                    this.cancel();
                                    return;
                                }

                                Location currentPos = arrow.getLocation();
                                double distance = lastPos.distance(currentPos);
                                Vector direction = currentPos.toVector().subtract(lastPos.toVector()).normalize();

                                for(double d = 0; d < distance; d += 0.5){
                                    Location loc = lastPos.clone().add(direction.clone().multiply(d));
                                    arrow.getWorld().spawnParticle(Particle.SMOKE,loc,1,0,0,0,0.01);
                                }

                                lastPos = currentPos.clone();
                            }
                        }.runTaskTimer(Main.getInstance(),0,1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onWeaponReloadingStart(PlayerInteractEvent event){
        if(event.getAction().isRightClick() && event.hasItem()){
            ItemStack weapon = event.getItem();
            if(weapon != null && weapon.getType() == Material.CROSSBOW){
                var meta = weapon.getItemMeta();
                Player player = event.getPlayer();

                if(meta instanceof CrossbowMeta cbMeta){
                    if(cbMeta.hasChargedProjectiles() || !player.getInventory().contains(Material.ARROW) && player.getGameMode() != GameMode.CREATIVE){
                        return;
                    }
                }

                if(!meta.hasItemModel()){
                    player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_start", 1f, 1f);
                    playWeaponReloadingMiddle(player,"zyneon:crossbow.crossbow_loading_middle");
                }
                else if(meta.hasItemModel()){
                    String weaponModel = meta.getItemModel().toString();

                    switch (weaponModel){
                        case String s when s.startsWith("zyneon:revolver"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.revolver_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.revolver_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:shotgun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.shotgun_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.shotgun_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:marksman_pistol"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.marksman_pistol_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.marksman_pistol_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:mauser_c96"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.mauser_c96_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.mauser_c96_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:lewis_gun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.lewis_gun_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.lewis_gun_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:luger"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.luger_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.luger_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:rifle"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.rifle_loading_start",1f,1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.rifle_loading_middle");
                            break;
                        default:
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player,"zyneon:crossbow.crossbow_loading_middle");
                            break;
                    }
                }
            }
        }
    }

    private void playWeaponReloadingMiddle(Player player, String sound){
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()->{
            if(player.isHandRaised()){
                player.getWorld().playSound(player.getLocation(), sound, 1f, 1f);
            }
        }, 10L);
    }

    @EventHandler
    public void onWeapomReloadingEnd(EntityLoadCrossbowEvent event){
        if(event.getEntity() instanceof Player player){
            var meta = event.getCrossbow().getItemMeta();
            if(!meta.hasItemModel()){
                player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_end", 1f, 1f);
            }
            else if(meta.hasItemModel()){
                String weaponModel = meta.getItemModel().toString();

                switch(weaponModel){
                    case String s when s.startsWith("zyneon:revolver"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.revolver_loading_end",1f,1f);
                        break;
                    case String s when s.startsWith("zyneon:shotgun"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.shotgun_loading_end",1f,1f);
                        break;
                    case String s when s.startsWith("zyneon:marksman_pistol"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.marksman_pistol_loading_end",1f,1f);
                        break;
                    case String s when s.startsWith("zyneon:mauser_c96"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.mauser_c96_loading_end",1f,1f);
                        meta.lore(List.of(Component.text("§7Ammo: §e8/8")));
                        event.getCrossbow().setItemMeta(meta);
                        break;
                    case String s when s.startsWith("zyneon:lewis_gun"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.lewis_gun_loading_end",1f,1f);
                        meta.lore(List.of(Component.text("§7Ammo: §e48/48")));
                        event.getCrossbow().setItemMeta(meta);
                        break;
                    case String s when s.startsWith("zyneon:luger"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.luger_loading_end",1f,1f);
                        meta.lore(List.of(Component.text("§7Ammo: §e8/8")));
                        event.getCrossbow().setItemMeta(meta);
                        break;
                    case String s when s.startsWith("zyneon:rifle"):
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.rifle_loading_end",1f,1f);
                        break;
                    default:
                        player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_end", 1f, 1f);
                        break;
                }
            }
        }
    }

    private void AutomaticReload(Player player, ItemStack weapon, int magSize){
        ItemMeta meta = weapon.getItemMeta();

        if (!meta.getPersistentDataContainer().has(ammoKey, PersistentDataType.INTEGER)) {
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
        }

        int currentAmmo = meta.getPersistentDataContainer().getOrDefault(ammoKey, PersistentDataType.INTEGER, 0);

        if(currentAmmo > 1){
            if(!player.getInventory().contains(Material.ARROW) && player.getGameMode() != GameMode.CREATIVE){
                meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
                meta.lore(List.of(Component.text("§7Ammo: §e0/"+magSize)));
                weapon.setItemMeta(meta);
                return;
            }

            if(player.getGameMode() != GameMode.CREATIVE){
                player.getInventory().removeItem(new ItemStack(Material.ARROW,1));
            }

            int newAmmo = currentAmmo - 1;
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, newAmmo);
            meta.lore(List.of(Component.text("§7Ammo: §e"+newAmmo+"/"+magSize)));

            weapon.setItemMeta(meta);

            new BukkitRunnable(){
                @Override
                public  void run(){
                    if(meta instanceof CrossbowMeta cbMeta){
                        cbMeta.addChargedProjectile(new ItemStack(Material.ARROW));
                        weapon.setItemMeta(cbMeta);
                    }
                }
            }.runTaskLater(Main.getInstance(),1L);
        }else{
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
            meta.lore(List.of(Component.text("§7Ammo: §e0/"+magSize)));
            weapon.setItemMeta(meta);
        }
    }
}
