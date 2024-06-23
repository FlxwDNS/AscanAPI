package de.flxwdev.ascan.misc;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public final class InventoryConfig {
    public InventoryConfig() {
        this.placeHolder = Material.BLACK_STAINED_GLASS_PANE;
        this.arrowLeftName = "§r§8» §6Letzte Seite";
        this.arrowRightName = "§r§8» §6Nächste Seite";
        this.arrowBackName = "§r§8» §6Zurück";
        this.pageSwitchSound = new InventorySound(Sound.BLOCK_MUD_PLACE, 1);
        this.pageSwitchErrorSound = new InventorySound(Sound.ENTITY_CHICKEN_EGG, 0);
        this.backPageSound = new InventorySound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1);
    }

    private Material placeHolder;
    private String arrowLeftName;
    private String arrowRightName;
    private String arrowBackName;
    private InventorySound pageSwitchSound;
    private InventorySound pageSwitchErrorSound;
    private InventorySound backPageSound;

    public InventoryConfig placeHolder(Material placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public InventoryConfig arrowLeftName(String arrowLeftName) {
        this.arrowLeftName = arrowLeftName;
        return this;
    }

    public InventoryConfig arrowRightName(String arrowRightName) {
        this.arrowRightName = arrowRightName;
        return this;
    }

    public InventoryConfig arrowBackName(String arrowBackName) {
        this.arrowBackName = arrowBackName;
        return this;
    }

    public InventoryConfig pageSwitchSound(InventorySound pageSwitchSound) {
        this.pageSwitchSound = pageSwitchSound;
        return this;
    }

    public InventoryConfig pageSwitchErrorSound(InventorySound pageSwitchErrorSound) {
        this.pageSwitchErrorSound = pageSwitchErrorSound;
        return this;
    }

    public InventoryConfig backPageSound(InventorySound backPageSound) {
        this.backPageSound = backPageSound;
        return this;
    }
}
