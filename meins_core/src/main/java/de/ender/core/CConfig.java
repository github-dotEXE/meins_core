package de.ender.core;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CConfig{

    private File customConfigFile;
    private String Fname;
    private JavaPlugin Fmain;
    private FileConfiguration customConfig;

    public CConfig(String name, JavaPlugin main){
        Fname = name;
        Fmain = main;
        createCustomConfig();
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(Fmain.getDataFolder(), Fname+".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Fmain.saveResource(Fname+".yml", false);
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
