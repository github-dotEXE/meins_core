package de.ender.core.resources;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class PluginResource {
    protected final String filename;
    protected final JavaPlugin plugin;

    public PluginResource(String filename, JavaPlugin plugin){
        this.filename = filename;
        this.plugin = plugin;
    }

    public String getFilename(){
        return filename;
    }

    public JavaPlugin getPlugin(){
        return plugin;
    }

    public abstract InputStream get();
    public FileConfiguration getAsYMLConfig(){
        return getAsConfig(new YamlConfiguration());
    }

    public <T extends FileConfiguration> FileConfiguration getAsConfig(T type){
        try {
            type.load(new InputStreamReader(get()));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    public BufferedImage getAsImage(){
        try {
            return ImageIO.read(get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
