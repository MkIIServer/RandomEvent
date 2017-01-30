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
        if(args.length < 1){
            sender.sendMessage("please enter event name: /event event_name");
            return true;
        }
        if(EventManager.isEventExist(args[0])){
            EventManager.spawnEvent(args[0]);
            return true;
        }
        sender.sendMessage("this event not exist");
        return true;
    }

}
