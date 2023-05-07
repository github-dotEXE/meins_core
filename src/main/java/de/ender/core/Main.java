package de.ender.core;

import de.ender.core.afk.AfkCMD;
import de.ender.core.afk.AfkManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static final String BUNGEE_DATA = "BungeeData";

    @Override
    public void onEnable() {
        Log.success("Enabling Meins Core...");
        Log.printLogo();
        plugin = this;
        UpdateChecker.check(getDescription().getVersion(), "github-dotEXE", "meins_core", "main");

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AfkManager(), this);
        pluginManager.registerEvents(new PluginMessageManager(), this);

        getCommand("afk").setExecutor(new AfkCMD());

        //PluginMessageManager
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageManager());

        //add players to afkManager after reload
        for(Player player : Bukkit.getOnlinePlayers()){
            AfkManager.playerJoin(player);
        }

        //try to auto re-initialize serverList
        PluginMessageManager.serversInit();
    }

    @Override
    public void onDisable() {
        Log.success("Disabling Meins Core");
        Log.printLogo();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
    public static Main getPlugin() {
        return plugin;
    }
}
