package com.zyneonstudios.nerotvlive.projectsbase.api.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Warp {

    private String name;
    private Location location;
    private boolean enabled = false;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(World world) {
        this.location.setWorld(world);
    }

    public void setWorld(String worldName) {
        this.location.setWorld(Bukkit.getWorld(worldName));
    }

    public void setX(double x) {
        this.location.setX(x);
    }

    public void setY(double y) {
        this.location.setY(y);
    }

    public void setZ(double z) {
        this.location.setZ(z);
    }

    public void setYaw(float yaw) {
        this.location.setYaw(yaw);
    }

    public void setPitch(float pitch) {
        this.location.setPitch(pitch);
    }

    public Warp(String name, String worldName, double x, double y, double z, float yaw, float pitch) {
        this(name, new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
    }

    public Warp(String name, World world, double x, double y, double z, float yaw, float pitch) {
        this(name,new Location(world, x, y, z, yaw, pitch));
    }

    public Warp(String name, String worldName, double x, double y, double z) {
        this(name, new Location(Bukkit.getWorld(worldName), x, y, z));
    }

    public Warp(String name, World world, double x, double y, double z) {
        this(name,new Location(world, x, y, z));
    }
}