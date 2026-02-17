package com.zyneonstudios.nerotvlive.projectsbase.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Communicator {

    public static boolean sendDebug = true;

    public static void broadcastRaw(String message) {
        sendRaw(Bukkit.getConsoleSender(),message);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendRaw(player,message);
        }
    }

    public static void broadcastInfo(String message) {
        sendInfo(Bukkit.getConsoleSender(),message);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendInfo(player,message);
        }
    }

    public static void broadcastWarning(String warning) {
        sendWarning(Bukkit.getConsoleSender(),warning);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendWarning(player,warning);
        }
    }

    public static void broadcastError(String error) {
        sendError(Bukkit.getConsoleSender(),error);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendError(player,error);
        }
    }

    public static void broadcastDebug(String debug) {
        sendDebug(Bukkit.getConsoleSender(),debug);
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendDebug(player,debug);
        }
    }

    public static void sendRaw(String message) {
        sendRaw(Bukkit.getConsoleSender(),message);
    }

    public static void sendInfo(String info) {
        sendInfo(Bukkit.getConsoleSender(),info);
    }

    public static void sendWarning(String warning) {
        sendWarning(Bukkit.getConsoleSender(),warning);
    }

    public static void sendError(String error) {
        sendError(Bukkit.getConsoleSender(),error);
    }

    public static void sendDebug(String debug) {
        sendDebug(Bukkit.getConsoleSender(),debug);
    }

    public static void sendRaw(CommandSender receiver, String message) {
        receiver.sendMessage(message);
    }

    public static void sendInfo(CommandSender receiver, String info) {
        sendRaw(receiver,Strings.prefix+info);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public static void sendWarning(CommandSender receiver, String warning) {
        sendRaw(receiver,"§6Warnung: §e"+warning);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
        }
    }

    public static void sendError(CommandSender receiver, String error) {
        sendRaw(receiver,"§4Fehler§8: §c"+error);
        if(receiver instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public static void sendDebug(CommandSender receiver, String debug) {
        if(sendDebug) {
            sendRaw(receiver,"§9Debug§8: §b"+debug);
        }
    }
}