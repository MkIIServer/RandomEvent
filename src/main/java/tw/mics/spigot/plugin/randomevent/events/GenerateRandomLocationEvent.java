package tw.mics.spigot.plugin.randomevent.events;

public class GenerateRandomLocationEvent implements AbstractEvent {
    @Override
    public String getEventName() {
        return "GENERATE_RANDOM_LOCATION";
    }
}
