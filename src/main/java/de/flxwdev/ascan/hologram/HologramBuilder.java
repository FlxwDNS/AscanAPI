package de.flxwdev.ascan.hologram;

import de.flxwdev.ascan.hologram.misc.Facing;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class HologramBuilder {
    @Getter
    private final static Map<HologramDisplay, UUID> cache = new HashMap<>();
    private final Location location;

    private Display.Billboard billboard;
    private Facing facing;
    private Color color;
    private float size;
    private boolean seeTrough;

    public HologramBuilder(Location location) {
        this.location = location;
        this.facing = Facing.NORTH;
        this.billboard = Display.Billboard.FIXED;
        this.size = 1;
        this.seeTrough = false;
        this.color = Color.BLACK.setAlpha(0);
    }

    public HologramBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public HologramBuilder size(float size) {
        this.size = size;
        return this;
    }

    public HologramBuilder seeTrough(boolean seeTrough) {
        this.seeTrough = seeTrough;
        return this;
    }

    public HologramBuilder billboard(Display.Billboard billboard) {
        this.billboard = billboard;
        return this;
    }

    public HologramBuilder facing(Facing facing) {
        this.facing = facing;
        return this;
    }

    public HologramDisplay build() {
        var tempLoc = this.location;
        tempLoc.setYaw(this.facing.getYaw());
        return new HologramDisplay(tempLoc, this.color, this.billboard, this.size, this.seeTrough);
    }

    public static HologramBuilder get(Location location) {
        return new HologramBuilder(location);
    }
}
