package tw.mics.spigot.plugin.randomevent.events;

public class GenerateRandomPlayerEvent implements AbstractEvent {
    @Override
    public String getEventName() {
        return "GENERATE_RANDOM_PLAYER";
    }
}
