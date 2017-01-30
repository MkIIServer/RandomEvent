package tw.mics.spigot.plugin.randomevent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import tw.mics.spigot.plugin.randomevent.command.EventCommand;
import tw.mics.spigot.plugin.randomevent.command.ItemsCommand;
import tw.mics.spigot.plugin.randomevent.config.Config;
import tw.mics.spigot.plugin.randomevent.config.Items;

public class RandomEvent extends JavaPlugin {
    static RandomEvent instance;

    @Override
    public void onEnable() {
        instance = this;
        ExecManager.init();
        Config.init();
        Config.load();
        Items.load();
        
        //register commands
        this.getCommand("event").setExecutor(new EventCommand(this));
        this.getCommand("items").setExecutor(new ItemsCommand(this));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static RandomEvent getInstance() {
        return instance;
    }
}
