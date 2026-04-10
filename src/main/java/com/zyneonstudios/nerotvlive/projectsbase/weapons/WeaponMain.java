package com.zyneonstudios.nerotvlive.projectsbase.weapons;

import com.zyneonstudios.nerotvlive.projectsbase.Main;

public class WeaponMain {

    public void load() {
        WeaponItems.init();
    }

    public void enable() {
        Main.getInstance().getServer().getPluginManager().registerEvents(new WeaponListener(), Main.getInstance());
        WeaponCommand weaponCommand = new WeaponCommand();
        Main.getInstance().getCommand("weapon").setExecutor(weaponCommand);
        Main.getInstance().getCommand("weapon").setTabCompleter(weaponCommand);
    }

    public void disable() {

    }
}