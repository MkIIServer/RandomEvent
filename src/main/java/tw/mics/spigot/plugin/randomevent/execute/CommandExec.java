package tw.mics.spigot.plugin.randomevent.execute;

import java.util.List;

public class CommandExec implements AbstractExec {
    @Override
    public String getEventName() {
        return "COMMAND";
    }

    @Override
    public void setParameter(String para) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public List<String> run(List<String> memory) {
        //TODO running
        return AbstractExec.initMemory(memory);
    }
}
