package de.ender.core;

import de.ender.core.afk.AfkCMD;
import de.ender.core.afk.AfkManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        Log.success("Enabling Meins Core");
        Log.printLogo();
        plugin = this;
        UpdateChecker.check("1.2", "github-dotEXE", "meins_core");

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AfkManager(), this);

        getCommand("afk").setExecutor(new AfkCMD());

        //add players after relaod
        for(Player player : Bukkit.getOnlinePlayers()){
            AfkManager.playerJoin(player);
        }
    }

    @Override
    public void onDisable() {
        Log.success("Disabling Meins Core");
        Log.printLogo();
    }
    public static Main getPlugin() {
        return plugin;
    }
}
