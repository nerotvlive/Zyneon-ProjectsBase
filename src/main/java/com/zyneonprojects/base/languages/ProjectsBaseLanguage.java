package com.zyneonprojects.base.languages;

import com.zyneonprojects.base.utilities.Strings;

public interface ProjectsBaseLanguage {

    String getId();
    String getName();

    String get(String key);
    default String get(Strings.Key key) { return get(key.name()); };

    boolean setValue(Strings.Key key, String value, boolean overwrite);
    boolean setValue(String key, String value, boolean overwrite);
    boolean setValue(Strings.Key key, String value);
    boolean setValue(String key, String value);
    boolean initialize();
    boolean reload();
    default boolean save() { return false; };
}