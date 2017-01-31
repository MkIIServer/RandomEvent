package tw.mics.spigot.plugin.randomevent.execute;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tw.mics.spigot.plugin.randomevent.RandomEvent;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteRunningException;
import tw.mics.spigot.plugin.randomevent.exception.ExecuteSetParameterException;

public class KeepEffectExec implements AbstractExec {
    private String target;
    private String effect;
    private String level;
    private String time;
    private String period;

    @Override
    public String getExecName() {
        return "KEEP_EFFECT";
    }

    @Override
    public void setParameter(String para) throws ExecuteSetParameterException {
        target = AbstractExec.getParameter("target", para);
        if(target == null){
            throw new  ExecuteSetParameterException("This execute have to add --target @all or --target player_name");
        }
        
        effect = AbstractExec.getParameter("effect", para);
        if(effect == null){
            throw new  ExecuteSetParameterException("This execute have to add --effect PotionEffectType");
        }
        
        level = AbstractExec.getParameter("level", para);
        if(level == null){
            level = "1";
        }
        
        time = AbstractExec.getParameter("time", para);
        if(time == null){
            throw new  ExecuteSetParameterException("This execute have to add --time time(sec)");
        }
        
        period = AbstractExec.getParameter("period", para);
        if(period == null){
            throw new  ExecuteSetParameterException("This execute have to add --period period(sec)");
        }
    }
    
    private class EffectRunnable implements Runnable{
        private int id;
        private long stop_timestramp;
        private String target;
        private PotionEffectType effect;
        private Integer level;
        private Integer period;
        public EffectRunnable(String target, PotionEffectType effect, Integer level, long timestramp,
                Integer period) {
            stop_timestramp = timestramp;
            this.effect = effect;
            this.level = level;
            this.period = period;
            this.target = target;
        }
        @Override
        public void run() {
            if(System.currentTimeMillis() > stop_timestramp){
                stop();
            } else {
                if(target.equals("@all")){
                    for(Player p: Bukkit.getOnlinePlayers()){
                        p.addPotionEffect(new PotionEffect(effect, period, level, true));
                    }
                } else {
                    Player p = Bukkit.getPlayer(target);
                    if(p == null || !p.isOnline()){
                        return;
                    }
                    p.addPotionEffect(new PotionEffect(effect, period, level, true));
                }
            }
        }
        
        public void setid(int id){
            this.id = id;
        }
        
        private void stop(){
            Bukkit.getScheduler().cancelTask(id);
        }
    }

    @Override
    public HashMap<String, String> run(HashMap<String, String> memory) throws ExecuteRunningException {
        memory = AbstractExec.initMemory(memory);
        long timestramp = System.currentTimeMillis();
        timestramp += Integer.parseInt(AbstractExec.replaceMemory(time, memory))*1000L;
        String target = AbstractExec.replaceMemory(this.target, memory);
        PotionEffectType effect = PotionEffectType.getByName(AbstractExec.replaceMemory(this.effect, memory));
        Integer level = Integer.parseInt(AbstractExec.replaceMemory(this.level, memory))-1;
        Integer period = Integer.parseInt(AbstractExec.replaceMemory(this.period, memory)) * 20;
        EffectRunnable effectrunnable = new EffectRunnable(target, effect, level, timestramp, period);
        effectrunnable.setid(
            Bukkit.getScheduler().scheduleSyncRepeatingTask(
                RandomEvent.getInstance(), 
                effectrunnable, 
                0, period
            )
        );
        return memory;
    }
}
