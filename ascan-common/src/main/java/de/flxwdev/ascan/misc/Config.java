package de.flxwdev.ascan.misc;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Accessors(fluent = true)
public final class Config {
    public Config() {
        this.placeHolder = Material.BLACK_STAINED_GLASS_PANE;

        this.arrowBack = new ItemStack(Material.ARROW);
        var arrowBackMeta = this.arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName("§r§8» §6Zurück");
        this.arrowBack.setItemMeta(arrowBackMeta);

        this.arrowLeft = new ItemStack(Material.ARROW);
        var arrowLeftMeta = this.arrowLeft.getItemMeta();
        arrowLeftMeta.setDisplayName("§r§8» §6Letzte Seite");
        this.arrowLeft.setItemMeta(arrowLeftMeta);

        this.arrowRight = new ItemStack(Material.ARROW);
        var arrowRightMeta = this.arrowRight.getItemMeta();
        arrowRightMeta.setDisplayName("§r§8» §6Nächste Seite");
        this.arrowRight.setItemMeta(arrowRightMeta);

        this.pageSwitchSound = new Audio(org.bukkit.Sound.BLOCK_MUD_PLACE, 1);
        this.pageSwitchErrorSound = new Audio(org.bukkit.Sound.ENTITY_CHICKEN_EGG, 0);
        this.backPageSound = new Audio(org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1);
    }
    private Material placeHolder;
    private ItemStack arrowBack;
    private ItemStack arrowLeft;
    private ItemStack arrowRight;

    private Audio pageSwitchSound;
    private Audio pageSwitchErrorSound;
    private Audio backPageSound;

    public Config placeHolder(Material placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public Config arrowLeft(ItemStack arrowLeft) {
        this.arrowLeft = arrowLeft;
        return this;
    }

    public Config arrowRight(ItemStack arrowRight) {
        this.arrowRight = arrowRight;
        return this;
    }

    public Config arrowBack(ItemStack arrowBack) {
        this.arrowBack = arrowBack;
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
