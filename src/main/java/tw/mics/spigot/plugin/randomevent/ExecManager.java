package tw.mics.spigot.plugin.randomevent;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteNotExistException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;
import tw.mics.spigot.plugin.randomevent.execute.AbstractExec;
import tw.mics.spigot.plugin.randomevent.execute.CommandExec;
import tw.mics.spigot.plugin.randomevent.execute.ExitIfExec;
import tw.mics.spigot.plugin.randomevent.execute.GenerateRandomLocationExec;
import tw.mics.spigot.plugin.randomevent.execute.GenerateRandomPlayerExec;
import tw.mics.spigot.plugin.randomevent.execute.MessageExec;

public class ExecManager {
    private static ExecManager instance;
    private static Logger logger;
    @SuppressWarnings("rawtypes")
    HashMap<String, Class> exec_list;
    
    @SuppressWarnings("rawtypes")
    ExecManager(){
        exec_list = new HashMap<String, Class>();
        registerExec(new CommandExec());
        registerExec(new ExitIfExec());
        registerExec(new GenerateRandomLocationExec());
        registerExec(new GenerateRandomPlayerExec());
        registerExec(new MessageExec());
    }
    
    public void registerExec(AbstractExec exec){
        String exec_name = exec.getExecName();
        if(exec_list.containsKey(exec_name)){
            logger.log(Level.WARNING, "Can't load execute " + exec_name);
            return;
        }
        exec_list.put(exec_name, exec.getClass());
    }
    
    public AbstractExec createExec(String name, String para) throws ExecuteNotExistException, ExecuteSetParameterException{
        if(!exec_list.containsKey(name))
            throw new ExecuteNotExistException(name + " execute is not exist");
        try {
            AbstractExec exec = (AbstractExec)exec_list.get(name).newInstance();
            exec.setParameter(para);
            return exec;
        } catch(ExecuteSetParameterException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new ExecuteNotExistException("create " + name + " new instance has unknow error");
        }
    }

    public static void init() {
        logger = RandomEvent.getInstance().getLogger();
        instance = new ExecManager();
    }
    
    public static ExecManager getInstange(){
        return instance;
    }
}
