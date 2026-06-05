package com.zyneonstudios.nerotvlive.projectsbase.custom;

import com.zyneonstudios.nerotvlive.projectsbase.Main;

public class CustomMain {

    public void load() {
        CustomItems.init();
    }

    public void enable() {
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArmorListener(), Main.getInstance());
    }

    public void disable() {

    }
}
