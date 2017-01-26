package tw.mics.spigot.plugin.randomevent.execute;

import java.util.List;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class MessageExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "MESSAGE";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        String msg = AbstractExec.getParameter(para, "msg");
        if(msg == null || msg.isEmpty()){
            throw new ExecuteSetParameterException("參數錯誤");
        }
    }
    
    @Override
    public List<String> run(List<String> memory) {
        //TODO running
        return AbstractExec.initMemory(memory);
    }
}
