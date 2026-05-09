package com.zyneonprojects.base.languages;

public abstract class InbuiltLanguage extends DefinedLanguage {

    private final String id;
    private final String name;

    public InbuiltLanguage(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract boolean onInitialization();
}
