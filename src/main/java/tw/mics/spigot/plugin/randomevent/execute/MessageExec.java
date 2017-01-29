package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class MessageExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "MESSAGE";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        //String msg = AbstractExec.getParameter(para, "msg");
        //if(msg == null || msg.isEmpty()){
        //    throw new ExecuteSetParameterException("參數錯誤");
        //}
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        // TODO Auto-generated method stub
        return AbstractExec.initMemory(memory);
    }
}
