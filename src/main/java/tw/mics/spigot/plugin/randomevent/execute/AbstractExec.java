package tw.mics.spigot.plugin.randomevent.execute;

import java.util.ArrayList;
import java.util.List;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public interface AbstractExec {
    public String getExecName();
    public void setParameter(String para) throws ExecuteSetParameterException;
    public List<String> run(List<String> memory);
    
    //Tools
    public static List<String> initMemory(List<String> memory){
        if(memory == null){
            memory = new ArrayList<String>();
        }
        return memory;
    }
    
    public static String getParameter(String raw,String setting){
        //TODO 取出該參數
        return raw;
    }
}
