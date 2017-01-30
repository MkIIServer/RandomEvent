package tw.mics.spigot.plugin.randomevent.execute;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class GenerateRandomPlayerExec implements AbstractExec {

    private Integer count;

    @Override
    public String getExecName() {
        return "GENERATE_RANDOM_PLAYER";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        if(para == null){
            count = 1;
        } else {
            try {
                count = Integer.parseInt(AbstractExec.getParameter("count", para));
            } catch (NumberFormatException e){
                throw new ExecuteSetParameterException("--count must be integer");
            }
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);;
        if(Bukkit.getOnlinePlayers().size() >= count){
            LinkedList<Player> players = new LinkedList<Player>();
            players.addAll(Bukkit.getOnlinePlayers());
            Collections.shuffle(players);
            for(int i=0; i<count; i++){
                memory.put("player" + String.valueOf(i+1), players.get(i).getName());
            }
        } else {
            throw new ExecuteRunningException("server players is less than count.");
        }
        return memory;
    }
}
