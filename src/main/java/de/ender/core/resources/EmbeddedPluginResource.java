package de.ender.core.resources;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public class EmbeddedPluginResource extends PluginResource {
    public EmbeddedPluginResource(String filename, JavaPlugin plugin) {
        super(filename, plugin);
    }

    public File saveInPluginsDataFolder(boolean replace){
        File file = new File(plugin.getDataFolder(), filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(filename, replace);
        }
        return file;
    }

    @Override
    public InputStream get(){
        InputStream is = plugin.getResource(filename);
        if(is==null) throw new NullPointerException();
        return is;
    }
}
