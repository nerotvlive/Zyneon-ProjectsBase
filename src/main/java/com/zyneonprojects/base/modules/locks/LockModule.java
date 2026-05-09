package com.zyneonprojects.base.modules.locks;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.modules.loader.modules.InbuiltModule;

public class LockModule extends InbuiltModule {

    public LockModule() {
        super("locks-and-trust","L&T");
    }

    @Override
    public ProjectsBase.ActivationStatus getActivationStatus() {
        return ProjectsBase.ActivationStatus.INCOMPATIBLE;
    }

    @Override
    public String getDescription() {
        return "Inbuilt Locks&Trust features module §c(not-implemented-yet)§r";
    }
}
