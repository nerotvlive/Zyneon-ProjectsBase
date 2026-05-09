package com.zyneonprojects.base.modules.roleplay;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.modules.loader.modules.InbuiltModule;

public class RoleplayModule extends InbuiltModule {

    public RoleplayModule() {
        super("roleplay","RPL");
    }

    @Override
    public ProjectsBase.ActivationStatus getActivationStatus() {
        return ProjectsBase.ActivationStatus.INCOMPATIBLE;
    }

    @Override
    public String getDescription() {
        return "Inbuilt roleplay features module §c(not-implemented-yet)§r";
    }
}
