package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class KeepMessageWhenJoinExec implements AbstractExec {
    private String target;
    private Object message;
    private String time;

    @Override
    public String getExecName() {
        return "KEEP_MESSAGE_WHEN_JOIN";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        target = AbstractExec.getParameter("target", para);
        if(target == null){
            throw new  ExecuteSetParameterException("This execute have to add --target @all or --target player_name");
        }
        
        message = AbstractExec.getParameter("msg", para);
        if(message == null){
            throw new  ExecuteSetParameterException("This execute have to add --message messages");
        }
        
        time = AbstractExec.getParameter("time", para);
        if(time == null){
            throw new  ExecuteSetParameterException("This execute have to add --time time(sec)");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);

        return memory;
    }
}
