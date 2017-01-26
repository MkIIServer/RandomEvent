package tw.mics.spigot.plugin.randomevent.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
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
        set_config_if_not_exist("general.autoevent.enable", true);
        set_config_if_not_exist("general.autoevent.event_period_min", 20);
        set_config_if_not_exist("general.autoevent.event_period_max", 40);
        YamlConfiguration events = new YamlConfiguration();
        events.set("ender_dragon.priority", 1);
        
        events.set("ender_dragon.event", new String[]{
                "GENERATE_RANDOM_LOCATION --world " + Bukkit.getWorlds().get(0).getName() + 
                    " --y-lower 255 --y-higher 150",
                "SPAWN_MOB --spawn ENDER_DRAGON --location {world} {x} {y} {z}",
                "BROADCAST --msg 終界產生了宇宙裂縫, 有一個終界龍被傳送到了 {world} 的 x:{x}, y:{y}, z:{z}",
            });
        
        events.set("give_diamond.priority", 5);
        events.set("give_diamond.event_effect", new String[]{
                "GENERATE_RANDOM_PLAYER",
                "MESSAGE --player {player} --msg 撿到鑽石拉!!!",
                "COMMAND --cmd give {player} diamond"
            });
        
        events.set("treasure.priority", 5);
        
        events.set("treasure.event", new String[]{
                "GENERATE_RANDOM_LOCATION --world " + Bukkit.getWorlds().get(0).getName() + 
                    " --y-lower 200 --y-higher 20",
                "SPAWN_TREASURE --location {world} {x} {y} {z} --random-x 20 --random-z 20",
                "BROADCAST --msg 已經在{world} 的  x:{x}, z:{z} 的±25格內, 高度 20~200 的位置放置了一個寶藏",
            });
        
        events.set("speed_dig.priority", 5);
        events.set("speed_dig.event", new String[]{
            "KEEP_EFFECT_ALL_PLAYER --effect FAST_DIGGING --level 1 --time 1200 --period 10",
            "KEEP_MESSAGE_WHEN_JOIN --time 1200 --msg 感覺手特別快, 趕快挖礦哦!",
        });
        
        events.set("never_hungry.priority", 5);
        events.set("never_hungry.event", new String[]{
            "KEEP_EFFECT_ALL_PLAYER --effect SATURATION --level 1 --time 1200 --period 10",
                "KEEP_MESSAGE_WHEN_JOIN --time 1200 --msg 剛剛吃太飽, 不會餓了!",
        });
        set_config_if_not_exist("events", events);
        
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
