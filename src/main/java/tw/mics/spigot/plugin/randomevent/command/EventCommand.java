package tw.mics.spigot.plugin.randomevent.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import tw.mics.spigot.plugin.randomevent.EventManager;
import tw.mics.spigot.plugin.randomevent.RandomEvent;

public class EventCommand implements CommandExecutor {
	RandomEvent plugin;
	public EventCommand(RandomEvent i){
		this.plugin = i;
	}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        EventManager.spawnEvent("give_diamond");
        return true;
    }

}
