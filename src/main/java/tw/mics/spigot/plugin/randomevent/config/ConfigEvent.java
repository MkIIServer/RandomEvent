package tw.mics.spigot.plugin.randomevent.config;

import java.util.ArrayList;
import java.util.List;

import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;

public class ConfigEvent {
    public Integer priority;
    public List<AbstractExec> executes;
    
    ConfigEvent(Integer priority){
        this.priority = priority;
        executes = new ArrayList<AbstractExec>();
    }
    
    public void addExec(AbstractExec exec) {
        executes.add(exec);
    }
}
