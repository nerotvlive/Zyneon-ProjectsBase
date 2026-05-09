package com.zyneonprojects.base.modules.loader.modules;

import com.zyneonprojects.base.ProjectsBase;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public interface ProjectsBaseModule {

    ProjectsBase.ActivationStatus getActivationStatus();
    ModuleFormat getModuleFormat();

    boolean load();
    boolean enable();
    boolean disable();
    boolean unload();
    void incompatible();
    void crash();

    String getId();
    String getName();
    String getDescription();
    String getVersion();
    String[] getAuthors();

    default String getAuthor() {
        if(getAuthors()==null||getAuthors().length==0) {
            return "Unknown author(s)";
        } else {
            return String.join(", ", getAuthors());
        }
    }

    void raw(String message);
    void log(String message);
    void info(String message);
    void warn(String message);
    void err(String message);
    void debug(String message);
    void trace(String message);
    void finest(String message);

    default boolean registerCommand(CommandExecutor executor, String... aliases) {
        return ProjectsBase.getInstance().registerCommand(this, executor, aliases);
    }

    default boolean registerListeners(Listener listenerClass) {
        return ProjectsBase.getInstance().registerListeners(this, listenerClass);
    }

    @Deprecated
    default boolean registerEvents(Listener listenerClass) {
        return registerListeners(listenerClass);
    }

    enum ModuleFormat {
        OBJECTv1,
        INBUILT,
        UNKNOWN,
    }
}
