package tw.mics.spigot.plugin.randomevent.command;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tw.mics.spigot.plugin.randomevent.RandomEvent;
import tw.mics.spigot.plugin.randomevent.config.Items;


public class ItemsCommand  implements CommandExecutor {
    RandomEvent plugin;
    public ItemsCommand(RandomEvent i){
        this.plugin = i;
    }
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
        if( arg3.length == 0 ){
            sendHelp(sender);
        } else if( arg3[0].equalsIgnoreCase("summon") ) {
            if(!(sender instanceof Player)){
                sender.sendMessage("§4this command must run on player");
            } else if(arg3.length == 1) {
                Block block = ((Player)sender).getLocation().getBlock();
                Items.setTreasure(block);
                sender.sendMessage("Treasure summon on your location.");
            } else {
                sendHelp(sender);
            }
        } else if (arg3[0].equalsIgnoreCase("additem")) {
            if(!(sender instanceof Player)){
                sender.sendMessage("§4this command must run on player");
                return true;
            } else if(arg3.length == 2) {
                try {
                    double chance = Double.valueOf(arg3[1]);
                    Player p = (Player) sender;
                    Items.addDrops(p.getInventory().getItemInMainHand(), chance);
                    sender.sendMessage("Items is added, and drops.yml is saved.");
                    return true;
                } catch (NumberFormatException err) {
                    sendHelp(sender);
                }
            } else {
                sendHelp(sender);
            }
        } else if (arg3[0].equalsIgnoreCase("reload")) {
            Items.load();
            sender.sendMessage("drops.yml loaded.");
        } else {
            sendHelp(sender);
        }
        return true;
    }
    
    private void sendHelp(CommandSender sender){
        sender.sendMessage("/items summon            - player only (summon chest at player location)");
        sender.sendMessage("/items additem <chance>  - this can add your on hand item to drops.yml");
        sender.sendMessage("/items reload            - reload drops.yml");
    }
}
