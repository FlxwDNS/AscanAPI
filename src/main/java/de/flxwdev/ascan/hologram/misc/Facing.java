package de.flxwdev.ascan.hologram.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Facing {
    NORTH(180f),
    EAST(-90f),
    SOUTH(0f),
    WEST(90f);

    private final float yaw;
}
