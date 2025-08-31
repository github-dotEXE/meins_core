package de.ender.core.resources;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DataFolderPluginResource extends PluginResource {
    private final File file;

    public DataFolderPluginResource(String filename, JavaPlugin plugin) {
        super(filename, plugin);
        this.file = new File(plugin.getDataFolder(), filename);
    }

    public DataFolderPluginResource(EmbeddedPluginResource embeddedPluginResource){
        super(embeddedPluginResource.getFilename(),embeddedPluginResource.getPlugin());
        this.file = embeddedPluginResource.saveInPluginsDataFolder(false);
    }

    @Override
    public InputStream get() {
        try {
            return Files.newInputStream(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
