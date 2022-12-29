package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        new MCore().log(ChatColor.GREEN + "Enabling Meins Core");
        new MCore().log();
        plugin = this;
        new UpdateChecker().check("1.0", "github-dotEXE", "meins_core");
    }

    @Override
    public void onDisable() {
        new MCore().log(ChatColor.GREEN + "Disabling Meins Core");
        new MCore().log();
    }
    public static Main getPlugin() {
        return plugin;
    }
}
