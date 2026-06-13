package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FixCommand implements CommandExecutor {

    @SuppressWarnings("all")
    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if(s.hasPermission("zyneon.fix")) {
            AtomicInteger fs1 = new AtomicInteger();
            AtomicInteger fs2 = new AtomicInteger();
            AtomicInteger fb1 = new AtomicInteger();
            AtomicInteger fb2 = new AtomicInteger();
            AtomicInteger fo1 = new AtomicInteger();
            AtomicInteger fo2 = new AtomicInteger();
            AtomicReference<String> fp1 = new AtomicReference<>("");
            AtomicReference<String> fp2 = new AtomicReference<>("");
            CompletableFuture.runAsync(() -> {
                File directory = new File("characters");
                int b = 0;
                int o = 0;
                if(directory.exists()&&directory.isDirectory()) {
                    String prefix = "[1,characters] ";
                    fp1.set(prefix);
                    Communicator.broadcastDebug(prefix+"Ordner gefunden!");
                    try {
                        File brokenFolder = new File("plugins/ProjectsBase/characters/outfits/broken");
                        if(!brokenFolder.exists()) {
                            brokenFolder.mkdirs();
                        }
                        if (Objects.requireNonNull(directory.listFiles()).length == 0) {
                            Communicator.broadcastDebug(prefix + "Ordner ist leer!");
                        } else {
                            Communicator.broadcastDebug(prefix + "Es wurden " + Objects.requireNonNull(directory.list()).length + " gefunden!");
                            for(File f: Objects.requireNonNull(directory.listFiles())) {
                                if(!f.isDirectory()) {
                                    Config config = new Config(f.getPath());
                                    if (config.get("outfit") != null) {
                                        if (config.get("outfit.url") != null) {
                                            if (config.get("outfit.url").toString().contains("http")) {
                                                o++;
                                                Communicator.broadcastDebug(prefix + "Datei " + f.getPath() + " ist nicht beschädigt! [" + o + "]");
                                            } else {
                                                b++;
                                                Communicator.broadcastDebug(prefix + "Datei " + f.getPath() + " ist beschädigt! [" + b + "]");
                                                File brokenFile = new File("plugins/ProjectsBase/characters/outfits/broken/" + f.getName());
                                                f.renameTo(brokenFile);
                                            }
                                        }
                                    }
                                }
                            }
                            fs1.set(Objects.requireNonNull(directory.list()).length);
                            fo1.set(o);
                            fb1.set(b);
                            Communicator.broadcastWarning(prefix + "Es wurden " + fs1 + " gefunden!");
                            Communicator.broadcastWarning(prefix + "Es sind " + fo1 + " Dateien nicht beschädigt!");
                            Communicator.broadcastWarning(prefix + "Es sind " + fb1 + " Dateien beschädigt!");
                        }
                    } catch (Exception e) {
                        Communicator.broadcastDebug(prefix + "Fehler: Ordner konnte nicht ausgelesen werden: "+e.getMessage());
                    }
                }
            }).thenRunAsync(() -> {
                File directory = new File("plugins/ProjectsBase/characters");
                int b = 0;
                int o = 0;
                if(directory.exists()&&directory.isDirectory()) {
                    String prefix = "[2,p/P/characters] ";
                    fp2.set(prefix);
                    Communicator.broadcastDebug(prefix+"Ordner gefunden!");
                    try {
                        File brokenFolder = new File("plugins/ProjectsBase/characters/broken");
                        if(!brokenFolder.exists()) {
                            brokenFolder.mkdirs();
                        }
                        if (Objects.requireNonNull(directory.listFiles()).length == 0) {
                            Communicator.broadcastDebug(prefix + "Ordner ist leer!");
                        } else {
                            Communicator.broadcastDebug(prefix + "Es wurden " + Objects.requireNonNull(directory.list()).length + " gefunden!");
                            for(File f: Objects.requireNonNull(directory.listFiles())) {
                                if(!f.isDirectory()) {
                                    Config config = new Config(f.getPath());
                                    if (config.get("character") != null) {
                                        if (config.get("character.name") != null) {
                                            if (!config.get("character.name").toString().contains("Unbekannt (")) {
                                                o++;
                                                Communicator.broadcastDebug(prefix + "Datei " + f.getPath() + " ist nicht beschädigt! [" + o + "]");
                                            } else {
                                                b++;
                                                Communicator.broadcastDebug(prefix + "Datei " + f.getPath() + " ist beschädigt! [" + b + "]");
                                                File brokenFile = new File("plugins/ProjectsBase/characters/broken/" + f.getName());
                                                f.renameTo(brokenFile);
                                            }
                                        }
                                    }
                                }
                            }
                            fs2.set(Objects.requireNonNull(directory.list()).length);
                            fo2.set(o);
                            fb2.set(b);
                            Communicator.broadcastWarning(prefix + "Es wurden " + fs2 + " gefunden!");
                            Communicator.broadcastWarning(prefix + "Es sind " + fo2 + " Dateien nicht beschädigt!");
                            Communicator.broadcastWarning(prefix + "Es sind " + fb2 + " Dateien beschädigt!");
                        }
                    } catch (Exception e) {
                        Communicator.broadcastDebug(prefix + "Fehler: Ordner konnte nicht ausgelesen werden: "+e.getMessage());
                    }
                }
            }).thenRunAsync(()->{
                Communicator.broadcastWarning(" ");
                Communicator.broadcastWarning(fp1 + "Es wurden " + fs1 + " gefunden!");
                Communicator.broadcastWarning(fp1 + "Es sind " + fo1 + " Dateien nicht beschädigt!");
                Communicator.broadcastWarning(fp1 + "Es sind " + fb1 + " Dateien beschädigt!");
                Communicator.broadcastWarning(" ");
                Communicator.broadcastWarning(fp2 + "Es wurden " + fs2 + " gefunden!");
                Communicator.broadcastWarning(fp2 + "Es sind " + fo2 + " Dateien nicht beschädigt!");
                Communicator.broadcastWarning(fp2 + "Es sind " + fb2 + " Dateien beschädigt!");
                Communicator.broadcastWarning(" ");
                Communicator.broadcastWarning("Es wurden insgesamt "+(fs1.get()+fs2.get())+" Dateien gefunden!");
                Communicator.broadcastWarning("Es sind "+(fo1.get()+fo2.get())+" Dateien nicht beschädigt!");
                Communicator.broadcastWarning("Es sind "+(fb1.get()+fb2.get())+" Dateien beschädigt!");
            });
        }
        return false;
    }
}
