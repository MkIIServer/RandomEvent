package tw.mics.spigot.plugin.randomevent;

import org.bukkit.plugin.java.JavaPlugin;

import tw.mics.spigot.plugin.randomevent.config.Config;

public class RandomEvent extends JavaPlugin {
    static RandomEvent instance;
    
    public void onEnable() {
        instance = this;
        EventManager.init();
        Config.load();
    }
    

    public static RandomEvent getInstance() {
        return instance;
    }
}
