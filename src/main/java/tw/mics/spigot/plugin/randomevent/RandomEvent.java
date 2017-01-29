package tw.mics.spigot.plugin.randomevent;

import org.bukkit.plugin.java.JavaPlugin;

import tw.mics.spigot.plugin.randomevent.command.EventCommand;
import tw.mics.spigot.plugin.randomevent.config.Config;

public class RandomEvent extends JavaPlugin {
    static RandomEvent instance;
    
    public void onEnable() {
        instance = this;
        ExecManager.init();
        Config.init();
        Config.load();
        
        //register commands
        this.getCommand("event").setExecutor(new EventCommand(this));
    }
    

    public static RandomEvent getInstance() {
        return instance;
    }
}
