package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
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
        String msg = ChatColor.translateAlternateColorCodes('&', AbstractExec.replaceMemory(message, memory));
        String p_str = AbstractExec.replaceMemory(target, memory);
        if(p_str.equals("@all")){
            Bukkit.broadcastMessage(msg);
        } else {
            Player p = Bukkit.getPlayer(p_str);
            if(p == null) {
               throw new ExecuteRunningException("Can't find this player!");
            } else {
                p.sendMessage(msg);
            }
        }
        return AbstractExec.initMemory(memory);
    }
}
