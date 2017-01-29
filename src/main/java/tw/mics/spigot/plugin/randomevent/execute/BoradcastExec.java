package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

public class BoradcastExec implements AbstractExec {
    public String getExecName() {
        return "BORADCAST";
    }

    @Override
    public void setParameter(String para) {
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        // TODO Auto-generated method stub
        return AbstractExec.initMemory(memory);
    }
}
