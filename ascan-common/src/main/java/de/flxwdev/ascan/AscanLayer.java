package de.flxwdev.ascan;


import de.flxwdev.ascan.misc.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Scanner;

public final class AscanLayer {
    @Getter
    private static JavaPlugin instance;
    @Getter
    private static Config config;

    public static void invoke(Class<? extends JavaPlugin> plugin) {
        var inputStream = AscanLayer.class.getClassLoader().getResourceAsStream("plugin.yml");
        if (inputStream == null) {
            throw new IllegalArgumentException("File 'plugin.yml' was not found!");
        } else {
            var scanner = new Scanner(inputStream).useDelimiter("\\A");
            //return scanner.hasNext() ? scanner.next() : "";
            System.out.println(scanner.hasNext() ? scanner.next() : "");
        }
    }

    public static void init(JavaPlugin plugin, Config config) {
        instance = plugin;
        AscanLayer.config = config;
    }
}
