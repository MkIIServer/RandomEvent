package tw.mics.spigot.plugin.randomevent;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import tw.mics.spigot.plugin.randomevent.config.Config;
import tw.mics.spigot.plugin.randomevent.config.ConfigEvent;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;

public class EventManager {
    public static void spawnRandomEvent(){
        int random = new Random().nextInt(Config.getTotalPriority())+1;
        HashMap<String, ConfigEvent> events = Config.getEvents();
        for(String ce_str: events.keySet()){
            ConfigEvent event = events.get(ce_str);
            random -= event.priority;
            if(random < 0){
                RandomEvent.getInstance().getLogger().log(Level.INFO, "Event " + ce_str + "spawning...");
                spawnEvent(ce_str);
                return;
            }
        }
    }
    
    public static void spawnEvent(String event_name){
        HashMap<String, String> memory = null;
        int exec_count = 1;
        try {
            for(AbstractExec e : Config.getEvent(event_name).executes){
                    memory = e.run(memory);
                    if(memory != null && memory.containsKey("exit") && memory.get("exit").equals("true")) break;
                    exec_count++;
            }
        } catch (ExecuteRunningException e) {
            String error_msg = new String();
            error_msg += System.lineSeparator() + "============================================================";
            error_msg += System.lineSeparator() + String.format("event %s have runtime error on execute line %d", event_name, exec_count);
            error_msg += System.lineSeparator() + "Exception: " + e.getClass().getSimpleName();
            error_msg += System.lineSeparator() + "Message: " + e.getErrorMessage();
            error_msg += System.lineSeparator() + "Memory List: ";
            if(memory == null){
                error_msg += System.lineSeparator() + "memory is null";
            } else {
                for(String m : memory.keySet()){
                    error_msg += System.lineSeparator() + String.format("%s :%s", m, memory.get(m));
                }
            }
            error_msg += System.lineSeparator() + "============================================================";
            RandomEvent.getInstance().getLogger().log(Level.WARNING, error_msg);
            e.getMessage();
        }
    }
    
    public static boolean isEventExist(String event_name){
        if(Config.getEvent(event_name) == null)return false;
        return true;
    }
}
