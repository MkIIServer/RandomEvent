package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class CommandExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "COMMAND";
    }
    private String command;

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        command = AbstractExec.getParameter("cmd", para);
        if(command == null){
            throw new  ExecuteSetParameterException("This execute have to add --cmd command");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        String cmd = AbstractExec.replaceMemory(command, memory);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
        return AbstractExec.initMemory(memory);
    }
}
