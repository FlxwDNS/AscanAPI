package de.flxwdev.ascan;

import de.flxwdev.ascan.hologram.listener.PlayerQuitListener;
import de.flxwdev.ascan.inventory.misc.InventoryConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AscanAPI {
    @Getter
    private static JavaPlugin instance;
    @Getter
    private static InventoryConfig config;

    public static void init(JavaPlugin plugin, InventoryConfig inventoryConfig) {
        instance = plugin;
        config = inventoryConfig;

        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), plugin);
    }
}
