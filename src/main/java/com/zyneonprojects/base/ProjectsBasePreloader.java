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
        Communicator.logInfo("§6BOT §8| §eInitializing ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+" preloader...");
        Communicator.logInfo("§6BOT §8| §eSuccessfully initialized ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+" preloader!");
        Communicator.logInfo("§6BOT §8| §eLoading ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        preloadConfig();
        projectsBase = new ProjectsBase(this);
        if(projectsBase.load()) {
            Communicator.logInfo("§6BOT §8| §eSuccessfully loaded ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
            System.gc();
        } else {
            Communicator.logErr("§6BOT §8| §eFailed to load ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
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
        Communicator.logInfo("§6BOT §8| §eEnabling ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        if(projectsBase.enable()) {
            Communicator.logInfo("§6BOT §8| §eSuccessfully enabled ProjectsBase " + getPluginMeta().getAPIVersion() + "-" + getPluginMeta().getVersion() + "!");
            System.gc();
        } else {
            Communicator.logErr("§6BOT §8| §eFailed to enable ProjectsBase " + getPluginMeta().getAPIVersion() + "-" + getPluginMeta().getVersion() + "!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Communicator.logInfo("§6BOT §8| §eDisabling ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"...");
        if(projectsBase.disable()) {
            Communicator.logInfo("§6BOT §8| §eSuccessfully disabled ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
            System.gc();
        } else {
            Communicator.logErr("§6BOT §8| §eFailed to disable ProjectsBase "+getPluginMeta().getAPIVersion()+"-"+getPluginMeta().getVersion()+"!");
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