package de.flxwdev.ascan.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public final class Audio {
    private final Sound sound;
    private final float pitch;
}
