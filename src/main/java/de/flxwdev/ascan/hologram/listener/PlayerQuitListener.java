package de.flxwdev.ascan.hologram.listener;

import de.flxwdev.ascan.hologram.HologramBuilder;
import de.flxwdev.ascan.hologram.HologramDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public final class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var cache = HologramBuilder.getCache().entrySet();

        for (Map.Entry<HologramDisplay, UUID> entry : cache) {
            if(event.getPlayer().getUniqueId().equals(entry.getValue())) {
                entry.getKey().destroy();
                HologramBuilder.getCache().remove(entry.getKey());
            }
        }

    }
}
