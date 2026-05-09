package com.zyneonprojects.base.languages;

import com.zyneonprojects.base.utilities.Strings;

public class EnglishLanguage extends InbuiltLanguage {

    public EnglishLanguage() {
        super("en", "English");
    }

    @Override
    public boolean onInitialization() {
        setValue(Strings.Key.error_console_only, "§cThis command can only be executed in the console§8!");
        setValue(Strings.Key.error_no_permission, "§cYou don§8'§ct have permission to do this§8!");
        setValue(Strings.Key.error_players_only, "§cThis command can only be executed as a player§8!");
        setValue(Strings.Key.error_player_not_found, "§cPlayer not found§8!");
        setValue(Strings.Key.error_player_not_found_placeholder, "§cThe player §4%player%§c could not be found§8!");
        return false;
    }
}
