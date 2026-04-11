package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.events.ZyneonChatEvent;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.workers.Banker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onZyneonChat(ZyneonChatEvent e) {
        Player p = e.getPlayer();
        User u = Main.getUser(p);
        if(u.isRoleplay()) {
            onRoleplayChat(u,e.getMessage());
            e.setCancelled(true);
            return;
        }
        if(u.getChatMode().equalsIgnoreCase("payout")) {
            e.setCancelled(true);
            String check = e.getMessage().split(" ", 2)[0];
            if (check.contains("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            try {
                int amount = Integer.parseInt(check);
                if(Banker.payout(p,amount)) {
                    Communicator.sendInfo(p,"Du hast dir §e"+amount+"§mM§7 auszahlen lassen§8.");
                    u.setChatMode("normal");
                    return;
                } else {
                    Communicator.sendError(p,"Dazu hast du nicht genug Geld auf dem Konto!");
                }
            } catch (NumberFormatException ex) {
                Communicator.sendError(p,"Du hast keinen gültigen Betrag eingegeben, bitte versuche es erneut.");
            }
            Communicator.sendWarning(p,"Schreibe \"cancel\" in den Chat, um die Auszahlung abzubrechen.");
        } else if(u.getChatMode().equalsIgnoreCase("character_name")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==2||numOfWords==3) {
                p.performCommand("char name "+e.getMessage());
                u.setChatMode("normal");
            } else {
                Communicator.sendError(p,"Du darfst maximal 3 und musst minimal 2 Wörter für einen Namen angeben§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_job")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==1) {
                p.performCommand("char job "+e.getMessage());
                u.setChatMode("normal");
            } else {
                Communicator.sendError(p,"Du darfst maximal ein Wort für deinen Job angeben§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_skin")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==1) {
                p.performCommand("char skin "+e.getMessage()+" ");
                u.setChatMode("normal");
            } else {
                Communicator.sendError(p,"Das ist keine gültige URL§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_variant")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==1) {
                if(e.getMessage().equalsIgnoreCase("SLIM")) {

                    u.setChatMode("character_skin");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-URL von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(e.getMessage().equalsIgnoreCase("CLASSIC")) {

                    u.setChatMode("character_skin");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-URL von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else {
                    Communicator.sendError(p,"Das ist keine gültige Variante, nutze \"SLIM\" oder \"CLASSIC\"§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
                }
            } else {
                Communicator.sendError(p,"Das ist keine gültige Variante, nutze \"SLIM\" oder \"CLASSIC\"§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(true);
        ZyneonChatEvent event = new ZyneonChatEvent(e.getPlayer(),e.getMessage());
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            Bukkit.broadcastMessage(event.getFormat());
        }
    }

    private void onRoleplayChat(User u, String message) {
        Player p = u.getPlayer();
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.getWorld() == p.getWorld() && all.getLocation().distance(p.getLocation()) <= 30) {
                Communicator.sendRaw(all,"§8[§6RP§8] §6"+u.getSelectedCharacter().getJob()+" §f• §e"+u.getSelectedCharacter().getName()+" » §7"+message);
            }
        }
    }
}