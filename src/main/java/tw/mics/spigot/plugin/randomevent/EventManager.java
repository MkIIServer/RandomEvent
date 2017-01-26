package tw.mics.spigot.plugin.randomevent;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import tw.mics.spigot.plugin.randomevent.events.AbstractEvent;
import tw.mics.spigot.plugin.randomevent.events.BoradcastEvent;

public class EventManager {
    private static EventManager instance;
    private static Logger logger;
    HashMap<String, AbstractEvent> event_list;
    
    EventManager(){
        event_list = new HashMap<String, AbstractEvent>();
        registerEvent(new BoradcastEvent());
    }
    
    public void registerEvent(AbstractEvent event){
        String event_name = event.getEventName();
        if(event_list.containsKey(event_name)){
            logger.log(Level.WARNING, "Can't load event " + event_name);
            return;
        }
        event_list.put(event_name, event);
    }

    public static void init() {
        logger = RandomEvent.getInstance().getLogger();
        instance = new EventManager();
    }
    
    public static EventManager getInstange(){
        return instance;
    }
}
