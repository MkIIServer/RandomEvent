package tw.mics.spigot.plugin.randomevent.execute;

import java.util.List;

public class BoradcastExec implements AbstractExec {
    public String getExecName() {
        return "BORADCAST";
    }

    @Override
    public void setParameter(String para) {
    }

    @Override
    public List<String> run(List<String> memory) {
        //TODO running
        return AbstractExec.initMemory(memory);
    }
}
