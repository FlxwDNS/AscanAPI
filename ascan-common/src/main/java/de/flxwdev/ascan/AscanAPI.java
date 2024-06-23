package de.flxwdev.ascan;

import de.flxwdev.ascan.misc.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class AscanAPI {
    @Getter
    private static JavaPlugin instance;
    @Getter
    private static Config config;

    public static void init(JavaPlugin plugin, Config config) {
        instance = plugin;
        AscanAPI.config = config;
    }
}
