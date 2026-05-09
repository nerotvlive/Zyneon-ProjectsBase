package com.zyneonprojects.base.modules.loader.modules;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.utilities.Communicator;

public abstract class DefinedModule implements ProjectsBaseModule {

    private ProjectsBase.ActivationStatus activationStatus = ProjectsBase.ActivationStatus.UNINITIALIZED;
    private ModuleFormat format = ModuleFormat.OBJECTv1;

    @Override
    public ProjectsBase.ActivationStatus getActivationStatus() {
        return activationStatus;
    }

    @Override
    public final boolean load() {
        if(activationStatus != ProjectsBase.ActivationStatus.UNLOADED&&activationStatus != ProjectsBase.ActivationStatus.UNINITIALIZED) {
            return activationStatus == ProjectsBase.ActivationStatus.LOADED;
        }
        activationStatus = ProjectsBase.ActivationStatus.LOADING;
        try {
            if (onLoad()) {
                activationStatus = ProjectsBase.ActivationStatus.LOADED;
                return true;
            }
        } catch (Exception e) {
            Communicator.sendErr(e.getMessage());
        }
        activationStatus = ProjectsBase.ActivationStatus.CRASHED;
        return false;
    }

    @Override
    public final boolean enable() {
        if(activationStatus != ProjectsBase.ActivationStatus.LOADED) {
            return activationStatus == ProjectsBase.ActivationStatus.ENABLED;
        }
        activationStatus = ProjectsBase.ActivationStatus.ENABLING;
        try {
            if (onEnable()) {
                activationStatus = ProjectsBase.ActivationStatus.ENABLED;
                return true;
            }
        } catch (Exception e) {
            Communicator.sendErr(e.getMessage());
        }
        activationStatus = ProjectsBase.ActivationStatus.CRASHED;
        return false;
    }

    @Override
    public final boolean disable() {
        if(activationStatus != ProjectsBase.ActivationStatus.ENABLED) {
            return activationStatus == ProjectsBase.ActivationStatus.DISABLED;
        }
        activationStatus = ProjectsBase.ActivationStatus.DISABLING;
        try {
            if (onDisable()) {
                activationStatus = ProjectsBase.ActivationStatus.DISABLED;
                return true;
            }
        } catch (Exception e) {
            Communicator.sendErr(e.getMessage());
        }
        activationStatus = ProjectsBase.ActivationStatus.CRASHED;
        return false;
    }

    @Override
    public final boolean unload() {
        if(activationStatus != ProjectsBase.ActivationStatus.DISABLED && activationStatus != ProjectsBase.ActivationStatus.CRASHED) {
            return activationStatus == ProjectsBase.ActivationStatus.UNLOADED;
        }
        activationStatus = ProjectsBase.ActivationStatus.UNLOADING;
        try {
            if(onUnload()) {
                activationStatus = ProjectsBase.ActivationStatus.UNLOADED;
                return true;
            }
        } catch (Exception e) {
            Communicator.sendErr(e.getMessage());
        }
        activationStatus = ProjectsBase.ActivationStatus.CRASHED;
        return false;
    }

    public boolean onLoad() {
        return true;
    }

    public boolean onEnable() {
        return true;
    }

    public boolean onDisable() {
        return true;
    }

    public boolean onUnload() {
        return true;
    }

    @Override
    public void incompatible() {
        if(activationStatus == ProjectsBase.ActivationStatus.UNINITIALIZED||activationStatus == ProjectsBase.ActivationStatus.LOADING) {
            activationStatus = ProjectsBase.ActivationStatus.INCOMPATIBLE;
        }
    }

    @Override
    public void crash() {
        disable();
        unload();
        activationStatus = ProjectsBase.ActivationStatus.CRASHED;
    }

    @Override
    public ModuleFormat getModuleFormat() {
        return format;
    }

    @Override
    public void raw(String message) {
        Communicator.sendRaw(null, "§f"+getName()+" §8| §7"+message);
    }

    @Override
    public void log(String message) {
        Communicator.logInfo("§f"+getName()+" §8| §7"+message);
    }

    @Override
    public void info(String message) {
        Communicator.logInfo("§f"+getName()+" §8| §7"+message);
    }

    @Override
    public void warn(String message) {
        Communicator.logWarn("§6"+getName()+" §8| §e"+message);
    }

    @Override
    public void err(String message) {
        Communicator.logErr("§4"+getName()+" §8| §c"+message);
    }

    @Override
    public void debug(String message) {
        Communicator.logDebug("§9"+getName()+" §8| §d"+message);
    }

    @Override
    public void trace(String message) {
        Communicator.logTrace("§4"+getName()+" §8| §c"+message);
    }

    @Override
    public void finest(String message) {
        Communicator.logFinest("§2"+getName()+" §8| §a"+message);
    }
}