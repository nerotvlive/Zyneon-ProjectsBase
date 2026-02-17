package com.zyneonstudios.nerotvlive.projectsbase;

import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.commands.*;
import com.zyneonstudios.nerotvlive.projectsbase.listeners.*;
import com.zyneonstudios.nerotvlive.projectsbase.locks.commands.LockCommand;
import com.zyneonstudios.nerotvlive.projectsbase.locks.commands.LockModeCommand;
import com.zyneonstudios.nerotvlive.projectsbase.locks.commands.TrustCommand;
import com.zyneonstudios.nerotvlive.projectsbase.locks.commands.UnlockCommand;
import com.zyneonstudios.nerotvlive.projectsbase.locks.listeners.LockInteractListener;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.LockManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Economy;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.Storage;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.MySQL;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.SQLite;
import com.zyneonstudios.nerotvlive.projectsbase.workers.Banker;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Config config = new Config("plugins/ProjectsBase/config.yml");
    public static HashMap<UUID, User> onlineUsers = new HashMap<>();
    public static ArrayList<String> whitelist;
    public static String version = "null";
    public static Main instance;
    public static Storage storage;
    public static Economy economy;
    public static boolean maintenance;

    @Override
    public void onLoad() {
        instance = this;
        version = getDescription().getVersion();
        Communicator.sendRaw("Loading §fProjectsBase§7 version §f"+version+"§8...");
        Communicator.sendRaw("§0");
        Communicator.sendInfo("Loading config.yml");
        checkConfig();
        Communicator.sendRaw("§0");
        Communicator.sendRaw("Successfully loaded §fProjectsBase§7 version §f"+version+"§8...");
    }

    @Override
    public void onEnable() {
        instance = this;
        checkConfig();
        initDatabase();
        WarpAPI.initAPI();
        if(config.getCFG().getBoolean("Settings.farmingWorld.enable")) {
            WorldCreator farmworld = new WorldCreator(config.getCFG().getString("Settings.farmingWorld.name"));
            Strings.farmWorldName = config.getCFG().getString("Settings.farmingWorld.name");
            Bukkit.getServer().createWorld(farmworld);
        }
        //WorldCreator world_argria = new WorldCreator("world_argria");
        //Bukkit.getServer().createWorld(world_argria);
        Communicator.sendRaw("§aEnabling §fProjectsBase§7 version §f"+version+"§8...");
        Communicator.sendRaw("§0");
        Communicator.sendInfo("Loading §elocks.yml§8...");
        LockManager.getLocks();
        Communicator.sendRaw("§0");
        getCommands();
        getListeners();
        economy = new Economy();
        Communicator.sendRaw("Successfully §aenabled §fProjectsBase§7 version §f"+version+"§8...");
        System.gc();
    }

    @Override
    public void onDisable() {
        Communicator.sendRaw("§cDisabling §fProjectsBase§7 version §f"+version+"§8...");
        Communicator.sendRaw("§0");
        Communicator.sendRaw("Successfully §cdisabled §fProjectsBase§7 version §f"+version+"§8...");
    }

    public static void checkConfig() {
        config = null;
        System.gc();
        config = new Config("plugins/ProjectsBase/config.yml");
        config.checkEntry("Settings.Strings.prefixWord","Projekt");
        Strings.setPrefixWord(config.getCFG().getString("Settings.Strings.prefixWord"));
        config.checkEntry("Settings.Lists.Whitelist",new ArrayList<String>());
        config.checkEntry("Settings.Lists.blockedCommands",new ArrayList<String>());
        config.checkEntry("Settings.storage","SQLite");
        config.checkEntry("Settings.farmingWorld.enable",false);
        config.checkEntry("Settings.farmingWorld.name","FW1");
        config.checkEntry("Settings.debug",false);
        config.checkEntry("Settings.maintenance",true);
        Communicator.sendDebug = config.getCFG().getBoolean("Settings.debug");
        maintenance = config.getCFG().getBoolean("Settings.maintenance");
        config.checkEntry("Settings.mySQL.host","127.0.0.1");
        config.checkEntry("Settings.mySQL.port","3306");
        config.checkEntry("Settings.mySQL.name","database");
        config.checkEntry("Settings.mySQL.user","root");
        config.checkEntry("Settings.mySQL.pass","password");
        whitelist = (ArrayList<String>) config.getCFG().getList("Settings.Lists.Whitelist");
        PlayerCommandListener.initBlockedCommands((ArrayList<String>)config.getCFG().getList("Settings.Lists.blockedCommands"));
    }

    private void initDatabase() {
        if(config.getCFG().getString("Settings.storage").equalsIgnoreCase("mysql")) {
            storage = new Storage(new MySQL(config.getCFG().getString("Settings.mySQL.host"), config.getCFG().getString("Settings.mySQL.port"), config.getCFG().getString("Settings.mySQL.name"), config.getCFG().getString("Settings.mySQL.user"), config.getCFG().getString("Settings.mySQL.pass"),false));
        } else if(config.getCFG().getString("Settings.storage").equalsIgnoreCase("sqlite")) {
            storage = new Storage(new SQLite("plugins/ProjectsBase/storage.db"));
        } else {
            storage = new Storage(new Config("plugins/ProjectsBase/storage.yml"));
        }
    }

    private void getCommands() {
        Communicator.sendRaw("Initialize §e§l§nCommands§8...");

        BroadcastCommand broadcastCommand = new BroadcastCommand();
        WarpCommand warpCommand = new WarpCommand();
        FeedCommand feedCommand = new FeedCommand();
        FlyCommand flyCommand = new FlyCommand();
        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        GodCommand godCommand = new GodCommand();
        HealCommand healCommand = new HealCommand();
        InvseeCommand invseeCommand = new InvseeCommand();
        MoneyCommand moneyCommand = new MoneyCommand();
        LockCommand lockCommand = new LockCommand();
        LockModeCommand lockmodeCommand = new LockModeCommand();
        PingCommand pingCommand = new PingCommand();
        PowerUserCommand powerUserCommand = new PowerUserCommand();
        SpeedCommand speedCommand = new SpeedCommand();
        SRLCommand srlCommand = new SRLCommand();
        TellCommand tellCommand = new TellCommand();
        TrustCommand trustCommand = new TrustCommand();
        WhitelistCommand whitelistCommand = new WhitelistCommand();

        initCommand(new AuthorCommand());
        initCommand(new BookCommand());
        initCommand(broadcastCommand,broadcastCommand);
        initCommand(new ClearchatCommand());
        initCommand(new CharacterCommand());
        initCommand(new DayCommand());
        initCommand(feedCommand,feedCommand);
        initCommand(flyCommand,flyCommand);
        initCommand(gamemodeCommand,gamemodeCommand);
        initCommand(godCommand,godCommand);
        initCommand(healCommand,healCommand);
        initCommand(invseeCommand,invseeCommand);
        initCommand(new MaintenanceCommand());
        initCommand(moneyCommand,moneyCommand);
        initCommand(lockCommand,lockCommand);
        initCommand(lockmodeCommand,lockmodeCommand);
        initCommand(new NightCommand());
        initCommand(pingCommand,pingCommand);
        initCommand(powerUserCommand,powerUserCommand);
        initCommand(new RainCommand());
        initCommand(new RoleplayCommand());
        initCommand(new SayCommand());
        initCommand(new ShoutCommand());
        initCommand(speedCommand,speedCommand);
        initCommand(srlCommand,srlCommand);
        initCommand(new SunCommand());
        initCommand(new TeamCommand());
        initCommand(tellCommand,tellCommand);
        initCommand(new ThunderCommand());
        initCommand(trustCommand,trustCommand);
        initCommand(new UnlockCommand());
        initCommand(warpCommand,warpCommand);
        initCommand(new WhisperCommand());
        initCommand(whitelistCommand,whitelistCommand);
        initCommand(new WorldCommand());

        Communicator.sendRaw("§0");
        Communicator.sendRaw("§0");
    }

    private void getListeners() {
        Communicator.sendRaw("Initialize §e§l§nListeners§8...");

        registerEvents(new ExplosionListener());
        registerEvents(new LockInteractListener());
        registerEvents(new InventoryClickListener());
        registerEvents(new PlayerChatListener());
        registerEvents(new PlayerCommandListener());
        registerEvents(new PlayerInteractListener());
        registerEvents(new PlayerJoinListener());
        registerEvents(new PlayerQuitListener());

        registerEvents(new Banker());

        Communicator.sendRaw("§0");
        Communicator.sendRaw("§0");
    }

    private void initCommand(CommandExecutor command) {
        Communicator.sendRaw("Loading §e"+command.getClass().getSimpleName()+"§7 (NT)§8...");
        getCommand(command.getClass().getSimpleName().toLowerCase().replace("command","")).setExecutor(command);
    }

    private void initCommand(CommandExecutor command, TabCompleter completer) {
        Communicator.sendRaw("Loading §e"+command.getClass().getSimpleName()+"§7 (TC)§8...");
        getCommand(command.getClass().getSimpleName().toLowerCase().replace("command","")).setExecutor(command);
        getCommand(command.getClass().getSimpleName().toLowerCase().replace("command","")).setTabCompleter(completer);

    }

    private void registerEvents(Listener listener) {
        Communicator.sendRaw("Registering §e"+listener.getClass().getSimpleName()+"§8...");
        Bukkit.getPluginManager().registerEvents(listener,this);
    }

    public static User getUser(UUID uuid) {
        if(onlineUsers.containsKey(uuid)) {
            return onlineUsers.get(uuid);
        }
        onlineUsers.put(uuid,new User(uuid));
        return getUser(uuid);
    }

    public static void setMaintenance(boolean maintenance) {
        Main.maintenance = maintenance;
        config.set("Settings.maintenance",maintenance);
        config.saveConfig();
        config.reloadConfig();
        if(maintenance) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                if(!all.hasPermission("zyneon.team")) {
                    all.kickPlayer("§cDer Wartungsmodus wurde aktiviert§8!");
                }
            }
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static User getUser(Player player) {
        return getUser(player.getUniqueId());
    }
}
