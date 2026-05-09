package com.zyneonprojects.base.modules.loader.modules;

import com.zyneonprojects.base.ProjectsBase;

public class InbuiltModule extends DefinedModule {

    private final String id;
    private final String name;
    private String description = "Inbuilt ProjectsBase module";

    public InbuiltModule(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public final String getVersion() {
        return ProjectsBase.getInstance().getVersion();
    }

    @Override
    public String[] getAuthors() {
        return ProjectsBase.getInstance().getAuthors();
    }

    @Override
    public final ModuleFormat getModuleFormat() {
        return ModuleFormat.INBUILT;
    }
}
