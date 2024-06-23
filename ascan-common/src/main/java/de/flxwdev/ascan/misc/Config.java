package de.flxwdev.ascan.misc;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public final class Config {
    public Config() {
        this.placeHolder = Material.BLACK_STAINED_GLASS_PANE;
        this.arrowLeftName = "§r§8» §6Letzte Seite";
        this.arrowRightName = "§r§8» §6Nächste Seite";
        this.arrowBackName = "§r§8» §6Zurück";
        this.pageSwitchSound = new Audio(org.bukkit.Sound.BLOCK_MUD_PLACE, 1);
        this.pageSwitchErrorSound = new Audio(org.bukkit.Sound.ENTITY_CHICKEN_EGG, 0);
        this.backPageSound = new Audio(org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1);
    }

    private Material placeHolder;
    private String arrowLeftName;
    private String arrowRightName;
    private String arrowBackName;
    private Audio pageSwitchSound;
    private Audio pageSwitchErrorSound;
    private Audio backPageSound;

    public Config placeHolder(Material placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public Config arrowLeftName(String arrowLeftName) {
        this.arrowLeftName = arrowLeftName;
        return this;
    }

    public Config arrowRightName(String arrowRightName) {
        this.arrowRightName = arrowRightName;
        return this;
    }

    public Config arrowBackName(String arrowBackName) {
        this.arrowBackName = arrowBackName;
        return this;
    }

    public Config pageSwitchSound(Audio pageSwitchSound) {
        this.pageSwitchSound = pageSwitchSound;
        return this;
    }

    public Config pageSwitchErrorSound(Audio pageSwitchErrorSound) {
        this.pageSwitchErrorSound = pageSwitchErrorSound;
        return this;
    }

    public Config backPageSound(Audio backPageSound) {
        this.backPageSound = backPageSound;
        return this;
    }
}
