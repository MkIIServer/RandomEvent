package tw.mics.spigot.plugin.randomevent.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import tw.mics.spigot.plugin.randomevent.RandomEvent;

public class Config {
    static YamlConfiguration cfg;
    static Boolean cfg_save_flag;
    
    
    public static void load() {
        RandomEvent.getInstance().getDataFolder().mkdirs();
        File f = new File(RandomEvent.getInstance().getDataFolder(), "config.yml");
        cfg = YamlConfiguration.loadConfiguration(f);
        
        cfg_save_flag = false;

        set_config_if_not_exist("general.debug", false);
        set_config_if_not_exist("general.event_period_min", 20);
        set_config_if_not_exist("general.event_period_max", 40);
        
        if(cfg_save_flag){
            try {
                cfg.save(f);
            } catch (IOException e) {
                RandomEvent.getInstance().getLogger().log(Level.WARNING, "config file can't save.");
            }
        }
    }

    private static void set_config_if_not_exist(String path, Object value){
        if(!cfg.contains(path)){
            cfg.set(path, value);
            cfg_save_flag = true;
        }
    }
}
