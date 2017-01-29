package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class ExitIfExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "EXIT_IF";
    }
    private Integer player_less_than;

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        player_less_than = Integer.parseInt(AbstractExec.getParameter("player-less-than", para));
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) {
        memory = AbstractExec.initMemory(memory);
        if(player_less_than != null && player_less_than > Bukkit.getServer().getOnlinePlayers().size()){
            memory.put("exit", "true");
        }
        return memory;
    }
}
