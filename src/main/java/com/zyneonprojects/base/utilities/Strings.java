package com.zyneonprojects.base.utilities;

import com.zyneonprojects.base.languages.EnglishLanguage;
import com.zyneonprojects.base.languages.ProjectsBaseLanguage;

public class Strings {

    private static ProjectsBaseLanguage language = new EnglishLanguage();

    public static String get(String key) {
        return language.get(key);
    }

    public static String get(Key key) {
        return language.get(key);
    }

    public static void setLanguage(ProjectsBaseLanguage language) {
        Strings.language = language;
    }

    public static ProjectsBaseLanguage getLanguage() {
        return language;
    }

    public enum Key {
        error_no_permission,
        error_players_only,
        error_player_not_found,
        error_player_not_found_placeholder,
        error_console_only,
        error_item_not_found,
        error_inventory_full,
        error_invalid_amount
    }
}