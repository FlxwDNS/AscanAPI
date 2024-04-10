package de.flxwdev.ascan.inventory.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Sound;

@Getter
@AllArgsConstructor
public final class InventorySound {
    private final Sound sound;
    private final float pitch;
}
