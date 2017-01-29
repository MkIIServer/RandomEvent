package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
            Player p = Bukkit.getPlayer(AbstractExec.replaceMemory(target, memory));
            if(p != null) p.sendMessage(msg);
        }
        return AbstractExec.initMemory(memory);
    }
}
