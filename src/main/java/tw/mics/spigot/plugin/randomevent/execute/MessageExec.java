package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class MessageExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "MESSAGE";
    }
    private String target;
    private String message;

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        target = AbstractExec.getParameter("target", para);
        message = AbstractExec.getParameter("msg", para);
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        String msg = AbstractExec.replaceMemory(message, memory);
        if(target.equals("@all")){
            Bukkit.broadcastMessage(msg);
        } else {
            Bukkit.getPlayer(AbstractExec.replaceMemory(target, memory)).sendMessage(msg);
        }
        return AbstractExec.initMemory(memory);
    }
}
