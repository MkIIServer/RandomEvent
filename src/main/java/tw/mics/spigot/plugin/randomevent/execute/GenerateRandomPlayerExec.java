package tw.mics.spigot.plugin.randomevent.execute;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class GenerateRandomPlayerExec implements AbstractExec {

    @Override
    public String getExecName() {
        return "GENERATE_RANDOM_PLAYER";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        if(para != null && !para.isEmpty()){
            throw new ExecuteSetParameterException("this command don't need any parameter.");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        memory = AbstractExec.initMemory(memory);;
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if(players.size() > 0){
            Player p = (Player) players.toArray()[(new Random()).nextInt(players.size())];
            memory.put("player", p.getName());
        }
        return memory;
    }
}
