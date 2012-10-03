package com.github.RadusAegis.HeroCiv;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    
    private boolean explode;
    private final FileConfiguration config;
    private final HeroCiv plugin;
    private final boolean destroyNoMoney;
    private final boolean destroyNoTown;
    
    public ConfigManager(FileConfiguration config, HeroCiv plugin) {
        this.config = config;
        this.plugin = plugin;
        
        //Parse region config data
        explode = config.getBoolean("explode-on-destroy", false);
        destroyNoMoney = config.getBoolean("destroy-if-no-money");
        destroyNoTown = config.getBoolean("destroy-if-not-part-of-town");
        loadCharters();
    }
    
    private void loadCharters() {
        Map<String, List<String>> charters = new HashMap<String, List<String>>();
        File charterFolder = new File(plugin.getDataFolder(), "charters");
        charterFolder.mkdirs();
        for (File charterFile : charterFolder.listFiles()) {
            FileConfiguration charterConfig = new YamlConfiguration();
            try {
                charterConfig.load(charterFile);
            } catch (Exception e) {
                plugin.warning("Failed to load charter " + charterFile.getName());
            }
            for (String key : charterConfig.getKeys(false)) {
                charters.put(key, charterConfig.getStringList(key));
                break;
            }
        }
        //send loaded charters for live use
        plugin.setCharters(charters);
    }
    
    public synchronized void writeToCharter(String name, List<String> data) {
        File charterFolder = new File(plugin.getDataFolder(), "charters");
        charterFolder.mkdirs();//Create the folder if it doesn't exist
        
        File charterData = new File(plugin.getDataFolder() + "/charters", name + ".yml");
        if (!charterData.exists()) {
            try {
                charterData.createNewFile();
            } catch (Exception e) {
                plugin.warning("Could not create new file " + name + ".yml");
                return;
            }
        }
        
        //Create the FileConfiguration to handle the new Charter
        FileConfiguration charterConfig = new YamlConfiguration();
        try {
            charterConfig.load(charterData);
        } catch (Exception e) {
            plugin.warning("Could not load charters.yml");
            return;
        }
        charterConfig.set(name, data);
    }
    
    public boolean getExplode() {
        return explode;
    }
    
    
    public boolean getDestroyNoMoney() {
        return destroyNoMoney;
    }
    
    public boolean getDestroyNoTown() {
        return destroyNoTown;
    }
    
    public synchronized void removeCharter(String name) {
        File charter = new File(plugin.getDataFolder() + "/charters", name + ".yml");
        if (!charter.exists()) {
            plugin.warning("Unable to delete non-existent charter " + name + ".yml");
            return;
        }
        if (!charter.delete()) {
            plugin.warning("Unable to delete charter " + name + ".yml");
        }
    }
}
