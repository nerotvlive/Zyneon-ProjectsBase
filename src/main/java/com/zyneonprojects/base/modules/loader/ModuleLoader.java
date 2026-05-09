package com.zyneonprojects.base.modules.loader;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.ProjectsBasePreloader;
import com.zyneonprojects.base.modules.loader.commands.ModulesCommand;
import com.zyneonprojects.base.modules.loader.modules.DefinedModule;
import com.zyneonprojects.base.modules.loader.modules.ProjectsBaseModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ModuleLoader extends DefinedModule {

    private static ModuleLoader instance = null;
    private static boolean enabled() {
        try {
            return ProjectsBase.getInstance().getConfig().getBoolean("settings.modules.loader.enable");
        } catch (Exception ignore) {}
        return false;
    }

    private HashMap<String, ProjectsBaseModule> modules = new HashMap<>();
    private ProjectsBase.ActivationStatus activationStatus = ProjectsBase.ActivationStatus.UNLOADED;

    public ModuleLoader(ProjectsBasePreloader plugin, ProjectsBase projectsBase) throws IllegalStateException {
        if(instance == null) {
            info("Initializing inbuilt module "+getName()+"@v"+getVersion()+"...");
            if ((plugin != null && plugin == ProjectsBasePreloader.getInstance()) && (projectsBase != null && projectsBase == ProjectsBase.getInstance())) {
                instance = this;
                info("Successfully initialized inbuilt module "+getName()+"@v"+getVersion()+"!");
                info(" ");
            } else {
                throw new IllegalStateException("ModuleLoader instance is not created properly! Please use ModuleLoader.getInstance() instead of new ModuleLoader! Do not use /reload, restart the server completely!");
            }
        } else {
            throw new IllegalStateException("ModuleLoader instance is already created! Please use ModuleLoader.getInstance() instead of new ModuleLoader! Do not use /reload, restart the server completely!");
        }
    }

    public Collection<String> getModuleIds() {
        if(!enabled()) { return new ArrayList<>(); }
        Collection<String> ids = modules.keySet();
        return ids;
    }

    public Collection<ProjectsBaseModule> getModules() {
        if(!enabled()) { return new ArrayList<>(); }
        Collection<ProjectsBaseModule> modules = this.modules.values();
        return modules;
    }

    public ProjectsBaseModule getModule(String id) {
        if(!enabled()) { return null; }
        if(id.equals(getId())) {
            return this;
        }
        return modules.get(id);
    }

    public boolean registerModule(ProjectsBaseModule module) {
        if(!enabled()) { return false; }

        String id = "null";
        String version = "0.0.0";
        String author = "Unknown author(s)";

        if (module != null) {
            if (module.getId() != null && !module.getId().isEmpty()) {
                id = module.getId();
                if (!(id.equals(getId()) || modules.containsKey(id))) {
                    if (module.getVersion() != null && !module.getVersion().isEmpty()) {
                        version = module.getVersion();
                    }
                    if (module.getAuthors() != null && module.getAuthors().length > 0) {
                        author = String.join(", ", module.getAuthors());
                    }
                    warn("Registering module " + id + "@v" + version + " by " + author + "...");
                    modules.put(id, module);
                    info("Successfully registered module " + id + "@v" + version + "!");
                    return true;
                } else {
                    err("Couldn't register module " + id + "@v" + version + ": Module id is already registered!");
                }
            } else {
                err("Couldn't register module " + id + "@v" + version + ": Module id cannot be null or empty!");
            }
        } else {
            err("Couldn't register module " + id + "@v" + version + ": Module cannot be null!");
        }
        return false;
    }

    public static ModuleLoader getInstance() {
        if(!enabled()) { return null; }
        return instance;
    }

    @Override
    public ProjectsBase.ActivationStatus getActivationStatus() {
        if(!enabled()) { return ProjectsBase.ActivationStatus.DISABLED; }
        return activationStatus;
    }

    @Override
    public String getId() {
        return "modules";
    }

    @Override
    public String getName() {
        return "Modules";
    }

    @Override
    public boolean onLoad() {
        if(!enabled()) { return false; }
        if(activationStatus == ProjectsBase.ActivationStatus.UNLOADED) {
            info("Loading inbuilt module "+getName()+"@v"+getVersion()+"...");
            activationStatus = ProjectsBase.ActivationStatus.LOADING;
            for(ProjectsBaseModule module : modules.values()) {
                warn("Found uninitialized "+module.getName()+"@v"+module.getVersion()+" by "+module.getAuthor()+"! Initializing...");
                if(module.getModuleFormat()==ModuleFormat.OBJECTv1||module.getModuleFormat()==ModuleFormat.INBUILT) {
                    info("Loading module "+module.getName()+"@v"+module.getVersion()+"...");
                    if(module.load()) {
                        info("Successfully loaded module "+module.getName()+"@v"+module.getVersion()+"!");
                    } else {
                        module.crash();
                        err("Couldn't load module "+module.getName()+"@v"+module.getVersion()+": Module crashed!");
                    }
                } else {
                    module.incompatible();
                    err("Couldn't load module "+module.getName()+"@v"+module.getVersion()+": Module is incompatible!");
                }
                info(" ");
            }
            activationStatus = ProjectsBase.ActivationStatus.LOADED;
            info("Successfully loaded inbuilt module "+getName()+"@v"+getVersion()+"!");
            info(" ");
            return true;
        }
        return activationStatus == ProjectsBase.ActivationStatus.LOADED;
    }

    @Override
    public boolean onEnable() {
        if(!enabled()) { return false; }
        if(activationStatus == ProjectsBase.ActivationStatus.LOADED) {
            info("Enabling inbuilt module "+getName()+"@v"+getVersion()+"...");
            activationStatus = ProjectsBase.ActivationStatus.ENABLING;
            for(ProjectsBaseModule module : modules.values()) {
                if(module.getActivationStatus().equals(ProjectsBase.ActivationStatus.LOADED)) {
                    warn("Found loaded "+module.getName()+"@v"+module.getVersion()+" by "+module.getAuthor()+"! Enabling...");
                    if(module.enable()) {
                        info("Successfully enabled module "+module.getName()+"@v"+module.getVersion()+"!");
                    } else {
                        err("Couldn't enable module "+module.getName()+"@v"+module.getVersion()+": Module crashed!");
                    }
                    info(" ");
                }
            }
            registerCommand(new ModulesCommand());
            activationStatus = ProjectsBase.ActivationStatus.ENABLED;
            info("Successfully enabled inbuilt module "+getName()+"@v"+getVersion()+"!");
            info(" ");
            return true;
        }
        return activationStatus == ProjectsBase.ActivationStatus.ENABLED;
    }

    @Override
    public boolean onDisable() {
        if(!enabled()) { return false; }
        if(activationStatus == ProjectsBase.ActivationStatus.ENABLED) {
            info("Disabling inbuilt module "+getName()+"@v"+getVersion()+"...");
            activationStatus = ProjectsBase.ActivationStatus.DISABLING;
            for(ProjectsBaseModule module : modules.values()) {
                if(!module.getActivationStatus().equals(ProjectsBase.ActivationStatus.DISABLED)&&!module.getActivationStatus().equals(ProjectsBase.ActivationStatus.CRASHED)) {
                    warn("Found loaded "+module.getName()+"@v"+module.getVersion()+" by "+module.getAuthor()+"! Disabling...");
                    if(module.disable()) {
                        info("Successfully disabled module "+module.getName()+"@v"+module.getVersion()+"!");
                    } else {
                        err("Couldn't disable module "+module.getName()+"@v"+module.getVersion()+": Module crashed! (FORCE DISABLED)");
                    }
                    info(" ");
                }
            }
            activationStatus = ProjectsBase.ActivationStatus.DISABLED;
            info("Successfully disabled inbuilt module "+getName()+"@v"+getVersion()+"!");
            info(" ");
            return true;
        }
        return activationStatus == ProjectsBase.ActivationStatus.DISABLED;
    }

    @Override
    public boolean onUnload() {
        if(!enabled()) { return false; }
        if(activationStatus == ProjectsBase.ActivationStatus.DISABLED) {
            info("Unloading inbuilt module "+getName()+"@v"+getVersion()+"...");
            activationStatus = ProjectsBase.ActivationStatus.UNLOADING;
            for(ProjectsBaseModule module : modules.values()) {
                if(!module.getActivationStatus().equals(ProjectsBase.ActivationStatus.UNLOADED)) {
                    warn("Found loaded "+module.getName()+"@v"+module.getVersion()+" by "+module.getAuthor()+"! Unloading...");
                    if(module.unload()) {
                        info("Successfully unloaded module "+module.getName()+"@v"+module.getVersion()+"!");
                    } else {
                        err("Couldn't unload module "+module.getName()+"@v"+module.getVersion()+": Module crashed! (FORCE UNLOADED)");
                    }
                    info(" ");
                }
            }
            modules.clear();
            modules = null;
            instance = null;
            activationStatus = ProjectsBase.ActivationStatus.UNLOADED;
            info("Successfully unloaded inbuilt module "+getName()+"@v"+getVersion()+"!");
            info(" ");
            return true;
        }
        return activationStatus == ProjectsBase.ActivationStatus.UNLOADED;
    }

    @Override
    public String getDescription() {
        return "Inbuilt modules features module";
    }

    @Override
    public String getVersion() {
        return ProjectsBase.getInstance().getVersion();
    }

    @Override
    public String[] getAuthors() {
        return ProjectsBase.getInstance().getAuthors();
    }


}