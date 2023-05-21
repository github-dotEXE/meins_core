package de.ender.core;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CConfig{

    private File customConfigFile;
    private String fileName;
    private final JavaPlugin plugin;
    private FileConfiguration customConfig;

    public CConfig(String name, JavaPlugin plugin){
        this.plugin = plugin;
        setFileName(name);
        createCustomConfig();
    }
    private void setFileName(String name){
        if(!name.contains(".yml")) this.fileName = name +".yml";
        else this.fileName = name;
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(plugin.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
