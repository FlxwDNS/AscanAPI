package de.flxwdev.ascan.inventory;

import com.google.common.collect.Multimap;
import de.flxwdev.ascan.AscanLayer;
import de.flxwdev.ascan.inventory.item.InteractItem;
import de.flxwdev.ascan.inventory.item.ItemView;
import de.flxwdev.ascan.inventory.item.SkullCreator;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class FilterView<T, D extends Enum<D>> extends SingletonView {
    private final Multimap<D, List<T>> values;
    private final List<D> categories;

    @Getter
    private int currentPage = 1;
    private int possibleAmount = 0;
    private int currentCategory = 0;

    private final InteractItem localLastPage = new InteractItem(ItemView.of(AscanLayer.getConfig().arrowLeft()), () -> {
        if(currentPage > 1) {
            createPage(--currentPage);
            player().playSound(player().getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
        } else {
            player().playSound(player().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
        }
    });
    private final InteractItem localNextPage = new InteractItem(ItemView.of(AscanLayer.getConfig().arrowRight()), () -> {
        if(currentPage != maxPage() && maxPage() > 1) {
            createPage(++currentPage);
            player().playSound(player().getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
        } else {
            player().playSound(player().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
        }
    });

    private InteractItem filterItem;

    public FilterView(Player player, Component name, int rows, boolean clickable, Multimap<D, List<T>> values, List<D> categories) {
        super(player, name, rows, clickable);
        this.categories = categories;

        clear();
        this.possibleAmount = (int) Arrays.stream(inventory().getContents()).filter(Objects::isNull).count();
        this.values = values;
        defineFilterItem();

        createPage(1);
    }

    private void defineFilterItem() {
        filterItem = new InteractItem(new ItemView(Material.SPYGLASS).name("§8» §6Filter").rawList(loreList()), () -> {
            currentCategory++;
            if(categories.size() < currentCategory) {
                Bukkit.getScheduler().runTaskLater(AscanLayer.getInstance(), () -> {
                    currentCategory = 0;
                    defineFilterItem();
                    createPage(currentPage);
                    player().playSound(player().getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
                }, 2);
                return;
            }
            defineFilterItem();
            createPage(currentPage);
            player().playSound(player().getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
        });
    }

    public abstract InteractItem constructItem(T value);

    public void createPage(int id) {
        this.currentPage = id;
        clear();

        item(inventory().getSize() / 9, 2, localLastPage);
        item(inventory().getSize() / 9, 6, localNextPage);
        item(inventory().getSize() / 9, 4, filterItem);

        List<T> tempList = new ArrayList<>();
        values.forEach((category, item) -> {
            if(category == currentCategory() || currentCategory == 0) tempList.addAll(item);
        });

        if(tempList.isEmpty()) {
            item(3, 4, ItemView.of(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/3cc470ae2631efdfaf967b369413bc2451cd7a39465da7836a6c7a14e877")).name("§8» §cDie Liste ist leer§8!"));
            return;
        }

        for (T element : tempList.subList(possibleAmount * (currentPage - 1), Math.min(tempList.size(), possibleAmount * (currentPage - 1) + possibleAmount))) {
            item(constructItem(element));
        }
    }

    private List<String> loreList() {
        List<String> loreList = new ArrayList<>();
        loreList.add("§8- " + ((currentCategory == 0) ? "§6" : "§7") + "Keinen");
        categories.forEach(it -> loreList.add("§8- " + ((it == currentCategory() ? "§6" : "§7") + it.name().toUpperCase().charAt(0) + it.name().toLowerCase().substring(1))));
        loreList.add("");
        loreList.add("§8» §6Linksklick §8- §7Filter ändern");
        return loreList;
    }

    private D currentCategory() {
        if(currentCategory == 0) return null;
        return categories.get(currentCategory - 1);
    }

    public void clear() {
        items().clear();

        for (int i = 0; i < 7; i++) item(2, i + 1, ItemView.of(Material.AIR));
        for (int i = 0; i < 7; i++) item(3, i + 1, ItemView.of(Material.AIR));
        for (int i = 0; i < 7; i++) item(4, i + 1, ItemView.of(Material.AIR));
        for (int i = 0; i < 7; i++) item(5, i + 1, ItemView.of(Material.AIR));
    }

    public int slot() {
        return inventory().getSize() - 18;
    }

    public int maxPage() {
        List<T> tempList = new ArrayList<>();
        values.forEach((category, item) -> {
            if(category == currentCategory() || currentCategory == 0) tempList.addAll(item);
        });

        return (int) Math.ceil((double) tempList.size() / possibleAmount);
    }
}
