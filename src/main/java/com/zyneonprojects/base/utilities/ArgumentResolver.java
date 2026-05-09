package com.zyneonprojects.base.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ArgumentResolver {

    public static Player[] getAta() {
        return Bukkit.getOnlinePlayers().toArray(new Player[0]);
    }

    public static Player[] getAta(World world) {
        return world.getPlayers().toArray(new Player[0]);
    }

    public static Entity[] getAte() {
        ArrayList<Entity> entities = new ArrayList<>();
        for(World world : Bukkit.getWorlds()) {
            entities.addAll(world.getEntities());
        }
        return entities.toArray(new Entity[0]);
    }

    public static Entity[] getAte(World world) {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(world.getEntities());
        return entities.toArray(new Entity[0]);
    }

    public static Entity getAtn(Location location) {
        Entity[] entities = getAte(location.getWorld());
        int distance = Integer.MAX_VALUE;
        Entity closest = null;
        for(Entity entity : entities) {
            if(entity.getLocation().distanceSquared(location) < distance) {
                closest = entity;
                distance = (int) entity.getLocation().distanceSquared(location);
            }
        }
        return closest;
    }

    public static Entity getAtn(Entity entity) {
        Entity closest = getAtn(entity.getLocation());
        if(closest == null) return entity;
        return closest;
    }

    public static Player getAtp(Location location) {
        Player[] players = getAta(location.getWorld());
        int distance = Integer.MAX_VALUE;
        Player closest = null;
        for(Player player : players) {
            if(player.getLocation().distanceSquared(location) < distance) {
                closest = player;
                distance = (int) player.getLocation().distanceSquared(location);
            }
        }
        return closest;
    }

    public static Player getAtp(Entity entity) {
        return getAtp(entity.getLocation());
    }

    public static Player getAtp(Player player) {
        Player closest = getAtp((Entity)player);
        if(closest == null) return player;
        return closest;
    }

    public static Player getAtr() {
        Player[] players = getAta();
        if (players.length > 0) {
            return players[ThreadLocalRandom.current().nextInt(players.length)];
        } else {
            return null;
        }
    }

    public static Player getAtr(Location location) {
        Player[] players = getAta(location.getWorld());
        if (players.length > 0) {
            return players[ThreadLocalRandom.current().nextInt(players.length)];
        } else {
            return null;
        }
    }

    public static Player getAtr(World world) {
        return getAtr(world.getSpawnLocation());
    }

    public static Player getAtr(Entity entity) {
        return getAtr(entity.getLocation());
    }

    public static Player getAtr(Player player) {
        Player r = getAtr((Entity)player);
        if(r == null) return player;
        return r;
    }

    public static Entity getAts(Entity entity) {
        return entity;
    }

    public static Player getAts(Player player) {
        return player;
    }
}