package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class KeepEffectExec implements AbstractExec {
    private String target;
    private String effect;
    private String level;
    private String time;
    private String period;

    @Override
    public String getExecName() {
        return "KEEP_EFFECT";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        target = AbstractExec.getParameter("target", para);
        if(target == null){
            throw new  ExecuteSetParameterException("This execute have to add --target @all or --target player_name");
        }
        
        effect = AbstractExec.getParameter("effect", para);
        if(effect == null){
            throw new  ExecuteSetParameterException("This execute have to add --effect PotionEffectType");
        }
        
        level = AbstractExec.getParameter("level", para);
        if(level == null){
            level = "1";
        }
        
        time = AbstractExec.getParameter("time", para);
        if(time == null){
            throw new  ExecuteSetParameterException("This execute have to add --time time(sec)");
        }
        
        period = AbstractExec.getParameter("period", para);
        if(period == null){
            throw new  ExecuteSetParameterException("This execute have to add --period period(sec)");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);

        return memory;
    }
}
