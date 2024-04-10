package de.flxwdev.ascan.hologram;

import de.flxwdev.ascan.AscanAPI;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.function.Function;

@Getter
public final class HologramDisplay {
    private final Location location;
    private final Color color;
    private final Display.Billboard billboard;
    private final float size;
    private final boolean seeTrough;

    private Function<Void, String> line;
    private TextDisplay entity;

    public HologramDisplay(Location location, Color color, Display.Billboard billboard, float size, boolean seeTrough) {
        this.location = location;
        this.color = color;
        this.billboard = billboard;
        this.size = size;
        this.seeTrough = seeTrough;
    }

    public void show(Player player, Function<Void, String> line) {
        this.line = line;

        var text = line.apply(null);
        var display = (TextDisplay) this.location.getWorld().spawn(this.location, TextDisplay.class);
        display.setGravity(false);
        display.text(Component.text(text));
        display.setAlignment(TextDisplay.TextAlignment.CENTER);
        display.setRotation(this.location.getYaw(), this.location.getPitch());
        display.setVisibleByDefault(false);
        display.setPersistent(false);
        display.setSeeThrough(this.seeTrough);
        display.setBillboard(this.billboard);
        display.setBackgroundColor(color);
        display.setTransformation(new Transformation(
                new Vector3f(0f, 0f, 1.0f),
                new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f),
                new Vector3f(this.size, this.size, this.size),
                new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f)
        ));

        player.showEntity(AscanAPI.getInstance(), display);

        this.entity = display;
        HologramBuilder.getCache().put(this, player.getUniqueId());
    }

    public void destroy() {
        this.entity.remove();
    }

    public void update() {
        this.entity.text(Component.text(line.apply(null)));
    }
}
