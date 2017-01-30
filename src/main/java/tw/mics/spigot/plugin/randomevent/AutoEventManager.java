package tw.mics.spigot.plugin.randomevent;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tw.mics.spigot.plugin.randomevent.config.Config;

public class AutoEventManager implements Listener {
    static AutoEventManager instance;
    Integer schedule_id;
    
    public AutoEventManager(){
        Bukkit.getPluginManager().registerEvents(this, RandomEvent.getInstance());
        
    }

    public static void init() {
        instance = new AutoEventManager();
        instance.checkSchedule();
    }
    
    private void checkScheduleSchedule(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomEvent.getInstance(), new Runnable(){
            @Override
            public void run() {
                checkSchedule();
            }
        });
    }

    private void checkSchedule() {
        if(Bukkit.getOnlinePlayers().size() < Config.getConfigInt("general.autoevent.min_player")){
            if(schedule_id != null){
                Bukkit.getScheduler().cancelTask(schedule_id);
            }
            return;
        } else {
            if(schedule_id == null){
                runSchedule();
            }
        }
    }
    
    private void runSchedule(){
        schedule_id = Bukkit.getScheduler().scheduleSyncDelayedTask(RandomEvent.getInstance(), new Runnable(){
            @Override
            public void run() {
                EventManager.spawnRandomEvent();
                runSchedule();
            }
        }, getRandom());
    }
    
    private int getRandom() {
        int min = Config.getConfigInt("general.autoevent.event_period_min_sec");
        int max = Config.getConfigInt("general.autoevent.event_period_max_sec");
        return (new Random().nextInt(max-min) + min) * 20;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        checkScheduleSchedule();
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        checkScheduleSchedule();
    }
}
