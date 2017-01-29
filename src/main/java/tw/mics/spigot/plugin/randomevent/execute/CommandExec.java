package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

public class CommandExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "COMMAND";
    }

    @Override
    public void setParameter(String para) {
        // TODO Auto-generated method stub
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        // TODO Auto-generated method stub
        return AbstractExec.initMemory(memory);
    }
}
