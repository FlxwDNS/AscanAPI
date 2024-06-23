package de.flxwdev.ascan.inventory.item;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.inventory.ItemStack;

@Accessors(fluent = true)
public final class InteractItem {
    @Getter
    private final ItemStack item;
    private final Runnable onClick;
    private final Runnable onMidClick;
    private final Runnable onRightClick;

    public InteractItem(ItemStack item, Runnable onClick) {
        this.item = item;
        this.onClick = onClick;
        this.onMidClick = onClick;
        this.onRightClick = onClick;
    }

    public InteractItem(ItemStack item, Runnable onLeftClick, Runnable onRightClick) {
        this.item = item;
        this.onClick = onLeftClick;
        this.onMidClick = () -> {};
        this.onRightClick = onRightClick;
    }

    public InteractItem(ItemStack item, Runnable onLeftClick, Runnable onMidClick, Runnable onRightClick) {
        this.item = item;
        this.onClick = onLeftClick;
        this.onMidClick = onMidClick;
        this.onRightClick = onRightClick;
    }

    public void click() {
        onClick.run();
    }

    public void clickMid() {
        onMidClick.run();
    }

    public void clickRight() {
        onRightClick.run();
    }
}
