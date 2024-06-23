package de.flxwdev.ascan;

import de.flxwdev.ascan.misc.InventoryConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class AscanAPI {
    @Getter
    private static JavaPlugin instance;
    @Getter
    private static InventoryConfig config;

    public static void init(JavaPlugin plugin, InventoryConfig inventoryConfig) {
        instance = plugin;
        config = inventoryConfig;
    }
}
