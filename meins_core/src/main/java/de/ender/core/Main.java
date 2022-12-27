package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //Branding
        new MCore().log(ChatColor.GREEN + "Enabling Meins Core");
        new MCore().log();
        //new CConfig("name", this).getCustomConfig();
    }

    @Override
    public void onDisable() {
        //Branding
        new MCore().log(ChatColor.GREEN + "Disabling Meins Core");
        new MCore().log();
    }
}
