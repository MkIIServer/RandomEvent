package tw.mics.spigot.plugin.randomevent.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tw.mics.spigot.plugin.randomevent.RandomEvent;

public class Items {
    private static ArrayList<Item> drops = null;
    private static YamlConfiguration drops_cfg;
    private static double chance_count;
    private static final File file = new File(RandomEvent.getInstance().getDataFolder(), "items.yml");

    // 取得掉落物品
    public static void setTreasure(Location block){
        setTreasure(block.getBlock());
    }
    public static void setTreasure(Block block){
        if(!(block instanceof InventoryHolder))block.setType(Material.CHEST);
        InventoryHolder chest = (InventoryHolder)block.getState();
        Inventory inv = chest.getInventory();
        for(ItemStack i: Items.getDrops(5)){
            inv.addItem(i);
        }
    }
    
    public static List<ItemStack> getDrops(int number) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < number; i++) {
            items.add(getRandomItemStack());
        }
        return items;
    }

    public static void addDrops(ItemStack item, double chance) {
        Item newDrop = new Item(item, chance);
        drops.add(newDrop);
        updateChanceCount();
        save();
    }

    private static ItemStack getRandomItemStack() {
        double random = (new Random().nextDouble()) * chance_count;
        for (Item drop : drops) {
            random -= drop.drop_chance;
            if (random < 0) {
                return drop.item;
            }
        }
        return null;
    }

    private static void save() {
        try {
            drops_cfg.set("Drops", drops.toArray());
            drops_cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateChanceCount() {
        chance_count = 0.0;
        for (Item d : drops) {
            chance_count += d.drop_chance;
        }
    }

    public static void load() {
        ConfigurationSerialization.registerClass(Item.class);
        drops_cfg = YamlConfiguration.loadConfiguration(file);
        drops = new ArrayList<Item>();
        chance_count = 0.0;
        if (drops_cfg.getList("Drops") == null) {
            makeDefaultDrops();
            save();
        } else {
            for (Object o : drops_cfg.getList("Drops")) {
                Item d = (Item) o;
                chance_count += d.drop_chance;
                drops.add((Item) o);
            }
        }
    }

    private static void makeDefaultDrops() {
        Item diamondDrop = new Item();

        ItemStack diamond = new ItemStack(Material.DIAMOND, 5);
        ItemMeta diamondMeta = diamond.getItemMeta();

        diamondMeta.setDisplayName("GOD DIAMOND");
        diamondMeta.setLore(Arrays.asList(new String[] { "YoyoTV", "HAHAHA" }));
        diamond.setItemMeta(diamondMeta);

        diamondDrop.item = diamond;
        diamondDrop.drop_chance = 50;

        Item appleDrop = new Item();
        ItemStack apple = new ItemStack(Material.APPLE, 5);
        appleDrop.item = apple;
        appleDrop.drop_chance = 50;

        if (drops_cfg == null)
            drops_cfg = new YamlConfiguration();
        drops = new ArrayList<Item>();
        drops.add(diamondDrop);
        drops.add(appleDrop);
    }
}