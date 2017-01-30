package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class SpawnMobExec implements AbstractExec {
    @Override
    public String getExecName() {
        return "SPAWN_MOB";
    }
    private String entity_type;
    private String location;

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        entity_type = AbstractExec.getParameter("entity-type", para);
        if(entity_type == null){
            throw new  ExecuteSetParameterException("This execute have to add --entity-type ENTITY_TYPE");
        }
        location = AbstractExec.getParameter("location", para);
        if(location == null){
            throw new  ExecuteSetParameterException("This execute have to add --location world x y z");
        } else if (location.split(" ").length != 4){
            throw new  ExecuteSetParameterException("This execute parameter format is wrong. --location world x y z");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        String[] loc_str = AbstractExec.replaceMemory(location, memory).split(" ");
        Location loc = new Location(
                Bukkit.getWorld(loc_str[0]),
                Double.parseDouble(loc_str[1]),
                Double.parseDouble(loc_str[2]),
                Double.parseDouble(loc_str[3])
            );
        loc.getChunk().load();
        loc.getWorld().spawnEntity(loc, EntityType.valueOf(AbstractExec.replaceMemory(entity_type, memory)));
        return AbstractExec.initMemory(memory);
    }
}
