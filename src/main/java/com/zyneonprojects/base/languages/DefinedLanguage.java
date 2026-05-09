package com.zyneonprojects.base.languages;

import com.zyneonprojects.base.utilities.Strings;

import java.util.HashMap;

public abstract class DefinedLanguage implements ProjectsBaseLanguage {

    private HashMap<String, String> values = new HashMap<>();

    @Override
    public abstract String getId();

    @Override
    public abstract String getName();

    @Override
    public String get(String key) {
        if(values.containsKey(key)) {
            return values.get(key);
        };
        return key;
    }

    @Override
    public String get(Strings.Key key) {
        return get(key.name());
    }

    @Override
    public boolean setValue(String key, String value, boolean overwrite) {
        if(!values.containsKey(key)||overwrite) {
            values.put(key, value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setValue(Strings.Key key, String value, boolean overwrite) {
        return setValue(key.name(), value, overwrite);
    }

    @Override
    public boolean setValue(Strings.Key key, String value) {
        return setValue(key.name(), value, false);
    }

    @Override
    public boolean setValue(String key, String value) {
        return setValue(key, value, false);
    }

    @Override
    public final boolean initialize() {
        return onInitialization();
    }

    public abstract boolean onInitialization();

    @Override
    public final boolean reload() {
        return onReload();
    }

    public boolean onReload() {
        return initialize();
    }

    @Override
    public boolean save() {
        return false;
    }
}