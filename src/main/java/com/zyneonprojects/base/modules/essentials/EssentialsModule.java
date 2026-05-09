package com.zyneonprojects.base.modules.essentials;

import com.zyneonprojects.base.ProjectsBase;
import com.zyneonprojects.base.modules.loader.modules.InbuiltModule;

public class EssentialsModule extends InbuiltModule {

    public EssentialsModule() {
        super("essentials","Essentials");
    }

    @Override
    public ProjectsBase.ActivationStatus getActivationStatus() {
        return ProjectsBase.ActivationStatus.INCOMPATIBLE;
    }

    @Override
    public String getDescription() {
        return "Inbuilt essentials features module §c(not-implemented-yet)§r";
    }
}
