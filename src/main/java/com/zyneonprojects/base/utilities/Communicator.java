package com.zyneonprojects.base.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Communicator {

    private static boolean debug = false;
    public static boolean isDebug() {
        return debug;
    }

    private static String PREFIX = "§9§lPB27 §8§l| §r§7";
    public static String getPrefix() {
        return PREFIX;
    }

    private static String ERROR_PREFIX = getPrefix() + "§4§lERR §8§l| §c§c";
    public static String getErrorPrefix() {
        return ERROR_PREFIX;
    }

    public static void setDebug(boolean debug) {
        Communicator.debug = debug;
    }

    public static void logRaw(String message) {
        sendRaw(null,message);
    }

    public static void logErr(String message) {
        sendErr(null,message);
    }

    public static void logWarn(String message) {
       sendWarn(null,message);
    }

    public static void logInfo(String message) {
        sendInfo(null,message);
    }

    public static void logDebug(String message) {
        if(!debug) return;
        sendDebug(null,message);
    }

    public static void logTrace(String message) {
        sendTrace(null,message);
    }

    public static void logFinest(String message) {
        sendFinest(null,message);
    }

    public static void sendRaw(CommandSender receiver, String message) {
        if(receiver == null) receiver = Bukkit.getConsoleSender();
        receiver.sendMessage(message);
    }

    public static void sendRaw(String message) {
        sendRaw(Bukkit.getConsoleSender(),message);
    }

    public static void sendErr(String message) {
        sendRaw(Bukkit.getConsoleSender(),ERROR_PREFIX+message);
    }

    public static void sendInfo(String message) {
        sendRaw(Bukkit.getConsoleSender(),getPrefix()+message);
    }

    public static void sendWarn(String message) {
        sendRaw(Bukkit.getConsoleSender(),getPrefix()+"§6§lWRN §8§l| §r§e"+message);
    }

    public static void sendDebug(String message) {
        if(!debug) return;
        sendRaw(Bukkit.getConsoleSender(),getPrefix()+"§9§lDEB §8§l| §r§b"+message);
    }

    public static void sendTrace(String message) {
        sendRaw(Bukkit.getConsoleSender(),getPrefix()+"§4§lTRA §8§l| §r§c"+message);
    }

    public static void sendFinest(String message) {
        sendRaw(Bukkit.getConsoleSender(),getPrefix()+"§2§lFIN §8§l| §r§a"+message);
    }

    public static void sendErr(CommandSender receiver, String message) {
        sendRaw(receiver,ERROR_PREFIX+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
        }
    }

    public static void sendInfo(CommandSender receiver, String message) {
        sendRaw(receiver,getPrefix()+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
        }
    }

    public static void sendWarn(CommandSender receiver, String message) {
        sendRaw(receiver,getPrefix()+"§6§lWRN §8§l| §r§e"+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }
    }

    public static void sendDebug(CommandSender receiver, String message) {
        if(!debug) return;
        sendRaw(receiver,getPrefix()+"§9§lDEB §8§l| §r§b"+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_STEP, 1, 1);
        }
    }

    public static void sendTrace(CommandSender receiver, String message) {
        sendRaw(receiver,getPrefix()+"§4§lTRA §8§l| §r§c"+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        }
    }

    public static void sendFinest(CommandSender receiver, String message) {
        sendRaw(receiver,getPrefix()+"§2§lFIN §8§l| §r§a"+message);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
    }
}