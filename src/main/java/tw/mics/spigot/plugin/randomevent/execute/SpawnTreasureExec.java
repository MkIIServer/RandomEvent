package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import tw.mics.spigot.plugin.randomevent.config.Items;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class SpawnTreasureExec implements AbstractExec {
    private String random_x;
    private String random_y;
    private String random_z;
    private String location;
    private String item_count;

    @Override
    public String getExecName() {
        return "SPAWN_TREASURE";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {        location = AbstractExec.getParameter("location", para);
        if(location == null){
            throw new  ExecuteSetParameterException("This execute have to add --location world x y z");
        } else if (location.split(" ").length != 4){
            throw new  ExecuteSetParameterException("This execute parameter format is wrong. --location world x y z");
        }
        random_x = AbstractExec.getParameter("random-x", para);
        if(random_x == null){
            random_x = "0";
        }
        random_y = AbstractExec.getParameter("random-y", para);
        if(random_y == null){
            random_y = "0";
        }
        random_z = AbstractExec.getParameter("random-z", para);
        if(random_z == null){
            random_z = "0";
        }
        item_count = AbstractExec.getParameter("item-count", para);
        if(item_count == null){
            throw new  ExecuteSetParameterException("This execute parameter format is wrong. --item-count 15");
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);
        Integer count = Integer.parseInt(AbstractExec.replaceMemory(item_count, memory));
        String[] loc_str = AbstractExec.replaceMemory(location, memory).split(" ");
        Integer random_x_int = Integer.parseInt(AbstractExec.replaceMemory(random_x, memory));
        Integer random_y_int = Integer.parseInt(AbstractExec.replaceMemory(random_y, memory));
        Integer random_z_int = Integer.parseInt(AbstractExec.replaceMemory(random_z, memory));
        Location loc = new Location(
                Bukkit.getWorld(loc_str[0]),
                Double.parseDouble(loc_str[1]),
                Double.parseDouble(loc_str[2]),
                Double.parseDouble(loc_str[3])
            );
        loc.add(getRandom(random_x_int), getRandom(random_y_int), getRandom(random_z_int));
        if(loc.getY() > 255)loc.setY(255);
        if(loc.getY() < 5)loc.setY(5);
        Items.setTreasure(loc, count);
        return memory;
    }
    
    private Integer getRandom(Integer random){
        if(random == 0) return 0;
        return (-random) + new Random().nextInt(random*2);
    }
}
