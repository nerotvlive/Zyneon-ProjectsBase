package com.zyneonprojects.base;

import com.zyneonprojects.base.modules.essentials.EssentialsModule;
import com.zyneonprojects.base.modules.loader.ModuleLoader;
import com.zyneonprojects.base.modules.loader.modules.ProjectsBaseModule;
import com.zyneonprojects.base.modules.locks.LockModule;
import com.zyneonprojects.base.modules.roleplay.RoleplayModule;
import com.zyneonprojects.base.modules.worlds.WorldsModule;
import com.zyneonprojects.base.utilities.Communicator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.zyneonstudios.apex.utilities.json.JsonFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ProjectsBase {

    private static ProjectsBase instance = null;

    public static ProjectsBase getInstance() {
        return instance;
    }

    private ActivationStatus state = ActivationStatus.UNLOADED;
    private String version = null;
    private String[] authors = null;
    private String author = null;

    protected ProjectsBase(ProjectsBasePreloader plugin) {
        if(instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException("ProjectsBase instance is already created! Please use ProjectsBase.getInstance() instead of new ProjectsBase! Do not use /reload, restart the server completely!");
        }
    }

    public ModuleLoader getModuleLoader() {
        return ModuleLoader.getInstance();
    }

    public ProjectsBasePreloader getPlugin() {
        return ProjectsBasePreloader.getInstance();
    }

    protected final boolean load() {
        if(state == ActivationStatus.UNLOADED) {
            state = ActivationStatus.LOADING;

            //INITIALIZING
            version = getPlugin().getPluginMeta().getVersion();
            authors = getPlugin().getPluginMeta().getAuthors().toArray(new String[0]);
            enableModulesModule();

            state = ActivationStatus.LOADED;
        }
        return state == ActivationStatus.LOADED;
    }

    private void enableModulesModule() {
        new ModuleLoader(getPlugin(), this);
        if(getConfig().getBoolean("settings.modules.loader.enable")) {
            registerModule("settings.modules.module.essentials.enable", new EssentialsModule());
            registerModule("settings.modules.module.locks.enable", new LockModule());
            registerModule("settings.modules.module.roleplay.enable", new RoleplayModule());
            registerModule("settings.modules.module.worlds.enable", new WorldsModule());
            getModuleLoader().load();
        }
    }

    private boolean registerModule(ProjectsBaseModule module) {
        return registerModule(null,module);
    }

    private boolean registerModule(String configKey, ProjectsBaseModule module) {
        if(module != null) {
            boolean enable = true;
            if(configKey!=null&&!configKey.isEmpty()) {
                try {
                    enable = getConfig().getBoolean(configKey);
                } catch (Exception _) {}
            }
            if(enable) {
                getModuleLoader().info("  - Registering module " + module.getId() + "...");
                return getModuleLoader().registerModule(module);
            }
        }
        return false;
    }

    protected final boolean enable() {
        if(state == ActivationStatus.LOADED) {
            state = ActivationStatus.ENABLING;
            if(getConfig().getBoolean("settings.modules.loader.enable")) {
                getModuleLoader().enable();
            }
            state = ActivationStatus.ENABLED;
        }
        return state == ActivationStatus.ENABLED;
    }

    protected final boolean disable() {
        if(state == ActivationStatus.ENABLED) {
            state = ActivationStatus.DISABLING;
            if(getConfig().getBoolean("settings.modules.loader.enable")) {
                getModuleLoader().disable();
                getModuleLoader().unload();
            }
            instance = null;
            state = ActivationStatus.DISABLED;
        }
        return state == ActivationStatus.DISABLED;
    }

    public ActivationStatus getState() {
        return state;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getAuthor() {
        if(author == null && authors != null && authors.length > 0) {
            String author = Arrays.toString(authors);
            author = author.replace("[", "").replace("]", "").replace(",", "");
            this.author = author;
        }
        return author;
    }

    private boolean registerListeners(Listener listenerClass) {
        Communicator.logInfo("  - Registering event listeners in "+listenerClass.getClass().getSimpleName()+"...");
        try {
            Bukkit.getPluginManager().registerEvents(listenerClass, getPlugin());
        } catch (Exception e) {
            Communicator.sendErr("    - Failed to register event linsters: "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean registerListeners(ProjectsBaseModule module, Listener listenerClass) {
        module.info("  - Registering event listeners in "+listenerClass.getClass().getSimpleName()+"...");
        try {
            Bukkit.getPluginManager().registerEvents(listenerClass, getPlugin());
        } catch (Exception e) {
            module.err("    - Failed to register event linsters: "+e.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("all")
    private boolean registerNewCommand(CommandExecutor executor, String... aliases) throws RuntimeException {
        String name = executor.getClass().getSimpleName().replace("Command", "").toLowerCase();
        try {
            final Field bucketCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bucketCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bucketCommandMap.get(Bukkit.getServer());
            BukkitCommand wrapper = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
                @Override
                public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
                    try {
                        return ((TabCompleter)executor).onTabComplete(sender, this, alias, args);
                    } catch (Exception e) {
                        return super.tabComplete(sender, alias, args);
                    }
                }
            };
            wrapper.setAliases(Arrays.asList(aliases));
            commandMap.register("projectsbase", wrapper);
            for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                player.updateCommands();
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean registerCommand(CommandExecutor commandClass, String... aliases) {
        String name = commandClass.getClass().getSimpleName().replace("Command", "").toLowerCase();
        Communicator.logInfo("  - Registering command /"+name+"...");
        try {
            registerNewCommand(commandClass, aliases);
        } catch (Exception e) {
            Communicator.sendErr("    - Failed to register command: "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean registerCommand(ProjectsBaseModule module, CommandExecutor commandClass, String... aliases) {
        String name = commandClass.getClass().getSimpleName().replace("Command", "").toLowerCase();
        module.info("  - Registering command /"+name+"...");
        try {
            registerNewCommand(commandClass, aliases);
        } catch (Exception e) {
            module.err("    - Failed to register command: "+e.getMessage());
            return false;
        }
        return true;
    }

    public JsonFile getConfig() {
        return ProjectsBasePreloader.getJsonConfig();
    }

    public enum ActivationStatus {
        UNINITIALIZED,
        CRASHED,
        INCOMPATIBLE,
        UNLOADED,
        LOADING,
        LOADED,
        ENABLING,
        ENABLED,
        DISABLING,
        DISABLED,
        UNLOADING
    }
}