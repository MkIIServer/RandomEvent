package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public interface AbstractExec {
    public String getExecName();
    public void setParameter(String para) throws ExecuteSetParameterException;
    HashMap<String, String> run(HashMap<String, String> memory);
    
    //Tools
    public static HashMap<String, String> initMemory(HashMap<String, String> memory){
        if(memory == null){
            memory = new HashMap<String, String>();
        }
        return memory;
    }
}
