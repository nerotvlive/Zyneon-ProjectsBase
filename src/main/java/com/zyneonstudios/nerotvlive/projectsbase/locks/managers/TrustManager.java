package com.zyneonstudios.nerotvlive.projectsbase.locks.managers;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TrustManager {

    private static final Storage s = Main.storage;

    public static boolean isTrusted(UUID check, UUID owner) {
        if(LockManager.enableLocks()) {
            return trustedUsers(owner).contains(check.toString());
        }
        return false;
    }

    public static void addTrusted(UUID uuid, UUID owner) {
        if(LockManager.enableLocks()) {
            ArrayList<String> list = trustedUsers(owner);
            ArrayList<String> who = trustedList(uuid);
            if (!list.contains(uuid.toString())) {
                list.add(uuid.toString());
                String save = String.join(",", list);
                s.setString("trust", owner.toString() + "_list", save, 0);
            }
            if (!who.contains(owner.toString())) {
                who.add(owner.toString());
                String save = String.join(",", who);
                s.setString("trust", uuid.toString() + "_who", save, 0);
            }
        }
    }

    public static void removeTrusted(UUID uuid, UUID owner) {
        if(LockManager.enableLocks()) {
            ArrayList<String> list = trustedUsers(owner);
            ArrayList<String> who = trustedList(uuid);
            if (list.contains(uuid.toString())) {
                list.remove(uuid.toString());
                String save = String.join(",", list);
                s.setString("trust", owner.toString() + "_list", save, 0);
            }
            if (who.contains(owner.toString())) {
                who.remove(owner.toString());
                String save = String.join(",", who);
                s.setString("trust", uuid.toString() + "_who", save, 0);
            }
        }
    }

    public static ArrayList<String> trustedUsers(UUID uuid) {
        if(LockManager.enableLocks()) {
            ArrayList<String> list = new ArrayList<>();
            if (s.get("trust", uuid.toString() + "_list", 0) != null) {
                if (!s.getString("trust", uuid.toString() + "_list", 0).contains("null")) {
                    if (!s.getString("trust", uuid.toString() + "_list", 0).equalsIgnoreCase("")) {
                        return new ArrayList<>(Arrays.asList(s.getString("trust", uuid.toString() + "_list", 0).split(",")));
                    }
                }
            }
            return list;
        }
        return new ArrayList<>();
    }

    public static ArrayList<String> trustedList(UUID uuid) {
        if(LockManager.enableLocks()) {
            ArrayList<String> list = new ArrayList<>();
            if (s.get("trust", uuid.toString() + "_who", 0) != null) {
                if (!s.getString("trust", uuid.toString() + "_who", 0).contains("null")) {
                    if (!s.getString("trust", uuid.toString() + "_who", 0).equalsIgnoreCase("")) {
                        return new ArrayList<>(Arrays.asList(s.getString("trust", uuid.toString() + "_who", 0).split(",")));
                    }
                }
            }
            return list;
        }
        return new ArrayList<>();
    }
}