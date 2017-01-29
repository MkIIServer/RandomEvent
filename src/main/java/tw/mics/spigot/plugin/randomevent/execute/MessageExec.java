package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
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
        if(target == null){
            throw new  ExecuteSetParameterException("This execute have to add --target @all or --target player_name");
        }
        message = AbstractExec.getParameter("msg", para);
        if(message == null){
            throw new  ExecuteSetParameterException("This execute have to add --msg Messages");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        String msg = AbstractExec.replaceMemory(message, memory);
        if(target.equals("@all")){
            Bukkit.broadcastMessage(msg);
        } else {
            Player p = Bukkit.getPlayer(AbstractExec.replaceMemory(target, memory));
            if(p == null) {
               throw new ExecuteRunningException("Can't find this player!");
            } else {
                p.sendMessage(msg);
            }
        }
        return AbstractExec.initMemory(memory);
    }
}
