package de.ender.core;

import de.ender.core.afk.AfkCMD;
import de.ender.core.afk.AfkManager;
import de.ender.core.guiManagers.GuiListener;
import de.ender.core.modifiers.ModifierManager;
import de.ender.core.weapons.Weapons;
import de.ender.core.weapons.WeaponsCMD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static final String BUNGEE_DATA = "BungeeData";

    @Override
    public void onEnable() {
        Log.enable(this);
        Log.printLogo();
        plugin = this;
        new UpdateChecker(this,"main").check().downloadLatestMeins();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new AfkManager(), this);
        pluginManager.registerEvents(new PluginMessageManager(), this);
        pluginManager.registerEvents(new ModifierManager(), this);
        pluginManager.registerEvents(new GuiListener(), this);

        getCommand("afk").setExecutor(new AfkCMD());
        getCommand("weapons").setExecutor(new WeaponsCMD());
        getCommand("weapons").setTabCompleter(new TabCompleter().addCompI(1, Weapons.getWeaponNames().toArray(new String[0])));

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
        Log.disable(this);
        Log.printLogo();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
    public static Main getPlugin() {
        return plugin;
    }
}
