package de.flxwdev.ascan.inventory;

import de.flxwdev.ascan.AscanLayer;
import de.flxwdev.ascan.inventory.item.InteractItem;
import de.flxwdev.ascan.inventory.item.ItemView;
import de.flxwdev.ascan.inventory.item.SkullCreator;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class PageableView<T> extends SingletonView {
    private final List<T> values;

    private final int rows;
    private int possibleAmount;

    private final List<Integer> cItems;

    @Setter
    private int slotLeft;
    @Setter
    private int slotRight;

    private SingletonView backPage;
    private InteractItem backItem;

    @Getter
    private int currentPage = 1;

    private final InteractItem localLastPage = new InteractItem(ItemView.of(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9")).name(AscanLayer.getConfig().arrowLeftName()), () -> {
        if(currentPage > 1) {
            createPage(--currentPage);
            var sound = AscanLayer.getConfig().pageSwitchSound();
            player().playSound(player().getLocation(), sound.sound(), 1, sound.pitch());
        } else {
            var sound = AscanLayer.getConfig().pageSwitchErrorSound();
            player().playSound(player().getLocation(), sound.sound(), 1, sound.pitch());
        }
    });
    private final InteractItem localNextPage = new InteractItem(ItemView.of(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf")).name(AscanLayer.getConfig().arrowRightName()), () -> {
        if(currentPage != maxPage() && maxPage() > 1) {
            createPage(++currentPage);
            var sound = AscanLayer.getConfig().pageSwitchSound();
            player().playSound(player().getLocation(), sound.sound(), 1, sound.pitch());
        } else {
            var sound = AscanLayer.getConfig().pageSwitchErrorSound();
            player().playSound(player().getLocation(), sound.sound(), 1, sound.pitch());
        }
    });

    public PageableView(Player player, Component name, int rows, boolean clickable, List<T> values) {
        super(player, name, rows, clickable, false);

        this.slotLeft = 3;
        this.slotRight = 5;

        this.cItems = new ArrayList<>();
        clear();
        //this.possibleAmount = (int) Arrays.stream(getInventory().getContents()).filter(Objects::isNull).count();
        this.values = values;
        this.rows = rows;

        this.backPage = null;
    }

    @Override
    public void open(Player player) {
        createPage(1);
        super.open(player);
    }

    @Override
    public void backPage(Class<? extends SingletonView> inventory) {
        this.backItem = new InteractItem(ItemView.of(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc")).name(AscanLayer.getConfig().arrowBackName()), () -> {
            try {
                inventory.getConstructors()[0].newInstance(placeHolder());
                var sound = AscanLayer.getConfig().backPageSound();
                player().playSound(player().getLocation(), sound.sound(), 1, sound.pitch());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public abstract InteractItem constructItem(T value);

    public void createPage(int id) {
        this.currentPage = id;
        clear();

        item(inventory().getSize() / 9, this.slotLeft, localLastPage);
        item(inventory().getSize() / 9, this.slotRight, localNextPage);
        if(backItem != null) {
            if (currentPage == 1) {
                placeHolder(rows, 4);
                item(rows, 2, backItem);
            } else {
                item(rows, 4, backItem);
            }
        }

        if(values.isEmpty()) {
            //item(3, 4, ItemView.of(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/3cc470ae2631efdfaf967b369413bc2451cd7a39465da7836a6c7a14e877")).name("§8» §cDie Liste ist leer§8!"));
        }

        for (T element : values.subList(possibleAmount * (currentPage - 1), Math.min(values.size(), possibleAmount * (currentPage - 1) + possibleAmount))) {
            cItems.add(item(constructItem(element)));
        }

    }

    public void clear() {
        cItems.forEach(it -> item(it, ItemView.of(Material.AIR)));

        this.possibleAmount = (int) Arrays.stream(inventory().getContents()).filter(Objects::isNull).count();
    }

    public int slot() {
        return inventory().getSize() - 18;
    }

    public int maxPage() {
        return (int) Math.ceil((double) values.size() / possibleAmount);
    }
}
