package tw.mics.spigot.plugin.randomevent.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Item implements ConfigurationSerializable {
    public ItemStack item;
    public double drop_chance;

    public Item() {
    }

    public Item(ItemStack item2, double chance) {
        item = item2;
        drop_chance = chance;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item", item);
        map.put("drop_chance", drop_chance);
        return map;
    }

    public static Item deserialize(Map<String, Object> map) {
        Item drop = new Item();
        drop.item = (ItemStack) map.get("item");
        drop.drop_chance = ((Number) map.get("drop_chance")).doubleValue();
        return drop;
    }
}