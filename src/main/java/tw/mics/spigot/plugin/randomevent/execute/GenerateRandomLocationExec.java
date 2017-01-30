package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class GenerateRandomLocationExec implements AbstractExec {

    private String world;
    private HashMap<String, Integer> max, min;
    
    public GenerateRandomLocationExec() {
        max = new HashMap<String, Integer>();
        min = new HashMap<String, Integer>();
    }

    @Override
    public String getExecName() {
        return "GENERATE_RANDOM_LOCATION";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        world = AbstractExec.getParameter("world", para);
        if(world == null){
            throw new  ExecuteSetParameterException("This execute have to add --world world");
        }
        setLocationMaxMin("x", para);
        setLocationMaxMin("y", para);
        setLocationMaxMin("z", para);
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);;
        World w = Bukkit.getWorld(world);
        memory.put("world", world); //TODO add random world
        memory.put("x", String.valueOf(getRandom(w.getWorldBorder(), "x")));
        memory.put("y", String.valueOf(getRandom(w.getWorldBorder(), "y")));
        memory.put("z", String.valueOf(getRandom(w.getWorldBorder(), "z")));
        
        return memory;
    }

    private void setLocationMaxMin(String xyz, String para) throws ExecuteSetParameterException{
        String max_str = AbstractExec.getParameter(xyz + "-lower", para);
        try{
            if(max_str != null) max.put(xyz, Integer.valueOf(max_str));
        } catch (NumberFormatException e){
            throw new ExecuteSetParameterException("--" + xyz + "-lower must be integer or empty(will get world border)");
        }
        String min_str = AbstractExec.getParameter(xyz + "-higher", para);
        try{
            if(min_str != null) min.put(xyz, Integer.valueOf(min_str));
        } catch (NumberFormatException e){
            throw new ExecuteSetParameterException("--" + xyz + "-higher must be integer or empty(will get world border)");
        }
    }
    
    private Integer getRandom(WorldBorder wb, String xyz) {
        Integer random_max = null, random_min = null;
        if(max.containsKey(xyz)){
            random_max = max.get(xyz);
        } else {
            if(xyz.equals("x"))
                random_max = (wb.getCenter().getBlockX() + (int)wb.getSize());
            else if(xyz.equals("y"))
                random_max = 255;
            else if(xyz.equals("z"))
                random_max = wb.getCenter().getBlockZ() + (int)wb.getSize();
        }
        if(min.containsKey(xyz)){
            random_min = max.get(xyz);
        } else {
            if(xyz.equals("x"))
                random_min = wb.getCenter().getBlockX() - (int)wb.getSize();
            else if(xyz.equals("y"))
                random_min = 6;
            else if(xyz.equals("z"))
                random_min = wb.getCenter().getBlockZ() - (int)wb.getSize();
        }
        return random_min + new Random().nextInt(random_max-random_min);
    }
}
