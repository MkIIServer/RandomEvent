package tw.mics.spigot.plugin.randomevent;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;
import tw.mics.spigot.plugin.randomevent.execute.BoradcastExec;

public class ExecManager {
    private static ExecManager instance;
    private static Logger logger;
    @SuppressWarnings("rawtypes")
    HashMap<String, Class> event_list;
    
    @SuppressWarnings("rawtypes")
    ExecManager(){
        event_list = new HashMap<String, Class>();
        registerEvent(new BoradcastExec());
    }
    
    public void registerEvent(AbstractExec event){
        String event_name = event.getEventName();
        if(event_list.containsKey(event_name)){
            logger.log(Level.WARNING, "Can't load event " + event_name);
            return;
        }
        event_list.put(event_name, event.getClass());
    }

    public static void init() {
        logger = RandomEvent.getInstance().getLogger();
        instance = new ExecManager();
    }
    
    public static ExecManager getInstange(){
        return instance;
    }
}
