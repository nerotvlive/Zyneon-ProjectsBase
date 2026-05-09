package com.zyneonprojects.base.utilities;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Permissions {

    public static HashMap<String, String> permissions = new HashMap<>();

    public static void registerPermission(String permission, String value) {
        permissions.put(permission,value);
    }

    public static String get(String permission) {
        if(permissions.containsKey(permission)) {
            permission = permissions.get(permission);
        }
        return permission;
    }

    public static boolean has(CommandSender sender, String permission) {
        return sender.hasPermission(get(permission))||sender.isOp();
    }
}

