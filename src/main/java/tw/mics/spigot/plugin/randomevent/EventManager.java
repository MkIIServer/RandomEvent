package tw.mics.spigot.plugin.randomevent;

import java.util.HashMap;

import tw.mics.spigot.plugin.randomevent.config.Config;
import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;

public class EventManager {
    public static void spawnEvent(String event_name){
        HashMap<String, String> memory = null;
        for(AbstractExec e : Config.getEvent(event_name).executes){
            memory = e.run(memory);
        }
    }
}
