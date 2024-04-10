package de.flxwdev.ascan.inventory.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class ClickableItem {
    @Getter
    private final ItemStack item;
    private final Runnable onClick;
    private final Runnable onMidClick;
    private final Runnable onRightClick;

    public ClickableItem(ItemStack item, Runnable onClick) {
        this.item = item;
        this.onClick = onClick;
        this.onMidClick = () -> {};
        this.onRightClick = () -> {};
    }

    public ClickableItem(ItemStack item, Runnable onLeftClick, Runnable onRightClick) {
        this.item = item;
        this.onClick = onLeftClick;
        this.onMidClick = () -> {};
        this.onRightClick = onRightClick;
    }

    public ClickableItem(ItemStack item, Runnable onLeftClick, Runnable onMidClick, Runnable onRightClick) {
        this.item = item;
        this.onClick = onLeftClick;
        this.onMidClick = onMidClick;
        this.onRightClick = onRightClick;
    }

    public void click() {
        onClick.run();
    }

    public void clickMid() {
        onRightClick.run();
    }

    public void clickRight() {
        onRightClick.run();
    }
}
