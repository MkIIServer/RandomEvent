package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public interface AbstractExec {
    public String getExecName();
    public void setParameter(String para) throws ExecuteSetParameterException;
    HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException;
    
    //Tools
    public static HashMap<String, String> initMemory(HashMap<String, String> memory){
        if(memory == null){
            memory = new HashMap<String, String>();
        }
        return memory;
    }
    
    public static String getParameter(String key, String para){
        Matcher m = Pattern.compile("--" + key + " [^-]*")
            .matcher(para);
        if(m.find()) {
            String[] key_and_value = m.group().split(" ", 2);
            if(key_and_value.length > 1){
                return key_and_value[1].trim();
            }
        }
        return null;
    }
    
    public static String replaceMemory(String str, HashMap<String, String> memory){
        for(String m : memory.keySet()){
            str = str.replaceAll(String.format("\\{%s\\}",m), memory.get(m));
        }
        return str.trim();
    }
}
