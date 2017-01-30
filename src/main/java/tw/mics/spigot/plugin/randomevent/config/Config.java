package tw.mics.spigot.plugin.randomevent.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import tw.mics.spigot.plugin.randomevent.ExecManager;
import tw.mics.spigot.plugin.randomevent.RandomEvent;
import tw.mics.spigot.plugin.randomevent.exception.RandomEventException;
import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;

public class Config {
    static YamlConfiguration cfg;
    static Boolean cfg_save_flag;
    
    public static void init() {
        RandomEvent.getInstance().getDataFolder().mkdirs();
        File f = new File(RandomEvent.getInstance().getDataFolder(), "config.yml");
        cfg = YamlConfiguration.loadConfiguration(f);
        
        cfg_save_flag = false;

        set_config_if_not_exist("general.debug", false);
        set_config_if_not_exist("general.autoevent.enable", true);
        set_config_if_not_exist("general.autoevent.min_player", 1);
        set_config_if_not_exist("general.autoevent.event_period_min_sec", 1200);
        set_config_if_not_exist("general.autoevent.event_period_max_sec", 2400);
        YamlConfiguration events = new YamlConfiguration();
        
        events.set("give_diamond.priority", 5);
        events.set("give_diamond.execute", new String[]{
                "EXIT_IF --player-less-than 1",
                "GENERATE_RANDOM_PLAYER --count 1",
                "MESSAGE --target @all --msg {player1} 撿到鑽石拉!!!",
                "COMMAND --cmd give {player1} diamond"
            });
        
        
        events.set("wither.priority", 1);
        events.set("wither.execute", new String[]{
                "GENERATE_RANDOM_LOCATION --world " + Bukkit.getWorlds().get(0).getName() + 
                    " --y-lower 180 --y-higher 150",
                "SPAWN_MOB --entity-type WITHER --location {world} {x} {y} {z}",
                "MESSAGE --target @all --msg 凋零在 {world} 的 x:{x}, y:{y}, z:{z} 現身了!!!",
            });
        
        events.set("treasure.priority", 5);
        
        events.set("treasure.execute", new String[]{
                "GENERATE_RANDOM_LOCATION --world " + Bukkit.getWorlds().get(0).getName() + 
                    " --y-lower 150 --y-higher 20",
                "SPAWN_TREASURE --item-count 6 --location {world} {x} {y} {z} --random-x 15 --random-y 15 --random-z 15",
                "MESSAGE --target @all --msg 已經在 {world} 的  x: {x}, y: {y} z: {z} 的 ±15 格內的位置放置了一個寶藏",
            });
        events.set("speed_dig.priority", 5);
        events.set("speed_dig.execute", new String[]{
            "GENERATE_RANDOM_PLAYER",
            "KEEP_EFFECT --target {player1} --effect FAST_DIGGING --level 2 --time 600 --period 5",
            "MESSAGE --target @all --msg &a{player1} 昨天晚上練了手速, 提升了 200% 現在挖礦很猛很強大!!",
            "KEEP_MESSAGE_WHEN_JOIN --target {player1} --time 600 --msg &a你的手速提升 200%, 趕快挖礦哦!",
        });
        
        events.set("never_hungry.priority", 5);
        events.set("never_hungry.execute", new String[]{
            "KEEP_EFFECT --target @all --effect SATURATION --level 1 --time 1200 --period 10",
            "KEEP_MESSAGE_WHEN_JOIN --target @all --time 1200 --msg 剛剛吃太飽, 不會餓了!",
        });
        set_config_if_not_exist("events", events);
        
        if(cfg_save_flag){
            try {
                cfg.save(f);
            } catch (IOException e) {
                RandomEvent.getInstance().getLogger().log(Level.WARNING, "config file can't save.");
            }
        }
        //reload
        cfg = YamlConfiguration.loadConfiguration(f);
    }


    static HashMap<String, ConfigEvent> events;
    public static void load() {
        events = new HashMap<String, ConfigEvent>();
        ConfigurationSection cfg_events = cfg.getConfigurationSection("events");
        cancel_this_event:
        for(String event_name : cfg_events.getKeys(false)){
            ConfigEvent event = new ConfigEvent(event_name, cfg_events.getInt(event_name + ".priority"));
            for(String exec_line : cfg_events.getStringList(event_name + ".execute")){
                String[] temp = exec_line.split(" ", 2);
                String exec_name = temp[0];
                String exec_para = temp.length == 2 ? temp[1] : null;
                try {
                    AbstractExec exec = ExecManager.getInstange().createExec(exec_name, exec_para);
                    event.addExec(exec);
                } catch (RandomEventException e) {
                    String error_msg = new String();
                    error_msg += System.lineSeparator() + "============================================================";
                    error_msg += System.lineSeparator() + String.format("event %s have parameter error on exec line: ", event_name );
                    error_msg += System.lineSeparator() +  exec_line;
                    error_msg += System.lineSeparator() + "Exception: " + e.getClass().getSimpleName();
                    error_msg += System.lineSeparator() + "Message: " + e.getErrorMessage();
                    error_msg += System.lineSeparator() + String.format("event %s is removed on list", event_name );
                    error_msg += System.lineSeparator() + "============================================================";
                    RandomEvent.getInstance().getLogger().log(Level.WARNING, error_msg);
                    continue cancel_this_event;
                }
            }
            events.put(event_name, event);
        }
    }
    
    public static ConfigEvent getEvent(String event_name){
        return events.get(event_name);
    }
    
    private static void set_config_if_not_exist(String path, Object value){
        if(!cfg.contains(path)){
            cfg.set(path, value);
            cfg_save_flag = true;
        }
    }
}
