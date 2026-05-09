package com.zyneonprojects.base.modules.worlds;

import com.zyneonprojects.base.modules.loader.modules.InbuiltModule;

public class WorldsModule extends InbuiltModule {

    public WorldsModule() {
        super("worlds", "Worlds");
    }

    @Override
    public String getDescription() {
        return "Inbuilt worlds management features module";
    }
}
