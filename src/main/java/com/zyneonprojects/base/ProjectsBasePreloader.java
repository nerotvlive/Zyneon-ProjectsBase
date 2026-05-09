package com.zyneonprojects.base;

import com.zyneonprojects.base.utilities.Communicator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.zyneonstudios.apex.utilities.json.JsonFile;

public class ProjectsBasePreloader extends JavaPlugin {

    private static ProjectsBase projectsBase = null;
    private static final JsonFile config = new JsonFile("plugins/ProjectsBase/config.json");
    private static ProjectsBasePreloader instance = null;

    @Override
    public void onLoad() {
        instance = this;
        Communicator.logWarn("[PRELOADER] Initializing ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+" preloader...");
        Communicator.logWarn("[PRELOADER] Successfully initialized ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+" preloader!");
        Communicator.logWarn("[PRELOADER] Loading ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        preloadConfig();
        projectsBase = new ProjectsBase(this);
        if(projectsBase.load()) {
            Communicator.logWarn("[PRELOADER] Successfully loaded ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
            System.gc();
        } else {
            Communicator.logErr("[PRELOADER] Failed to load ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void preloadConfig() {
        config.setPrettyPrint(true);
        config.ensure("settings.modules.loader.enable",true);
        config.ensure("settings.modules.module.essentials.enable",true);
        config.ensure("settings.modules.module.locks.enable",false);
        config.ensure("settings.modules.module.roleplay.enable",false);
    }

    @Override
    public void onEnable() {
        Communicator.logWarn("[PRELOADER] Enabling ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        if(projectsBase.enable()) {
            Communicator.logWarn("[PRELOADER] Successfully enabled ProjectsBase " + getPluginMeta().getAPIVersion() + "-" + getPluginMeta().getVersion() + "!");
            System.gc();
        } else {
            Communicator.logErr("[PRELOADER] Failed to enable ProjectsBase " + getPluginMeta().getAPIVersion() + "-" + getPluginMeta().getVersion() + "!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Communicator.logWarn("[PRELOADER] Disabling ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        if(projectsBase.disable()) {
            Communicator.logWarn("[PRELOADER] Successfully disabled ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
            System.gc();
        } else {
            Communicator.logErr("[PRELOADER] Failed to disable ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
        }
    }

    /**
     * @deprecated This uses the old/default plugin configuration system (yml) this plugin is using our own json based configuration system. Use ProjectsBase.getJsonConfig() instead.
     * @return FileConfiguration("plugins/ProjectsBase/config.yml");
     */
    @Override @Deprecated(forRemoval = true) @SuppressWarnings("all")
    public final FileConfiguration getConfig() {
        return super.getConfig();
    }

    /**
     * This returns the main json configuration file for the ProjectsBase.
     * @return JsonFile("plugins/ProjectsBase/config.json");
     */
    protected static JsonFile getJsonConfig() {
        return config;
    }

    public static ProjectsBasePreloader getInstance() {
        return instance;
    }
}