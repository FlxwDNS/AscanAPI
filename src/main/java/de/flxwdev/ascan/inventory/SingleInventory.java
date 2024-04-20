package de.flxwdev.ascan.inventory;

import de.flxwdev.ascan.AscanAPI;
import de.flxwdev.ascan.inventory.item.ClickableItem;
import de.flxwdev.ascan.inventory.item.ItemBuilder;
import dev.dbassett.skullcreator.SkullCreator;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class SingleInventory implements Listener {
    @Getter
    private final Inventory inventory;
    @Getter
    private final Map<Integer, ClickableItem> items;
    private final Boolean clickable;
    private final int rows;

    @Getter
    private final Player player;

    @Getter
    private Material placeholder;

    public SingleInventory(Player player, String name, int rows, boolean clickable) {
        this(player, name, rows, clickable, true);
    }

    public SingleInventory(Player player, String name, int rows, boolean clickable, boolean open) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, rows * 9, Component.text("§7" + name + " §8(§7" + player.getName() + "§8)"));
        this.items = new HashMap<>();
        this.clickable = clickable;
        this.rows = rows;
        this.placeholder = AscanAPI.getConfig().getPlaceHolder();

        AscanAPI.getInstance().getServer().getPluginManager().registerEvents(this, AscanAPI.getInstance());

        if(open) {
            open(player);
        }
    }

    public void customPlaceholder(Material placeholder) {
        this.placeholder = placeholder;
    }

    public void setBackPage(Class<? extends SingleInventory> inventory) {
        var item = new ClickableItem(ItemBuilder.of(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc")).withName("§8» §6Zurück"), () -> {
            try {
                inventory.getConstructors()[0].newInstance(player);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        if (getItemStack(rows, 4).getType().equals(placeholder)) {
            setClickableItem(rows, 4, item);
        } else if (getItemStack(rows, 5).getType().equals(placeholder)) {
            setClickableItem(rows, 3, item);
            this.inventory.setItem(((rows - 1) * 9) + 5, getItemStack(rows, 4));
            setItem(rows, 4, ItemBuilder.of(placeholder).withName("§7 "));
        } else {
            setClickableItem(rows, 3, item);
        }
    }

    public ItemStack getItemStack(int row, int slot) {
        return inventory.getItem(((row - 1) * 9) + slot);
    }

    public void addItem(ItemStack item) {
        inventory.addItem(item);
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        items.remove(slot);
    }

    public void setItem(int row, int slot, ItemStack item) {
        inventory.setItem(((row - 1) * 9) + slot, item);
        items.remove(((row - 1) * 9) + slot);
    }

    public void open(Player player) {
        if (!(player.getOpenInventory().getType().equals(InventoryType.CHEST))) {
            //player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1f, 1f);
        } else {
            //player.playSound(player.getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
        }
        player.openInventory(inventory);
    }

    public void setClickableItem(int slot, ClickableItem clickableItem) {
        inventory.setItem(slot, clickableItem.getItem());
        items.put(slot, clickableItem);
    }

    public void setClickableItem(int row, int slot, ClickableItem clickableItem) {
        inventory.setItem(((row - 1) * 9) + slot, clickableItem.getItem());
        items.put(((row - 1) * 9) + slot, clickableItem);
    }

    public int addClickableItem(ClickableItem clickableItem) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, clickableItem.getItem());
                items.put(i, clickableItem);
                return i;
            }
        }
        return 0;
    }

    public void setPlaceHolder(int row) {
        for (int i = 0; i < 9; i++) setPlaceHolder(row, i);
    }

    public void setPlaceHolder(int row, int slot) {
        setItem(row, slot, ItemBuilder.of(new ItemStack(placeholder)).withName("§7 "));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        if (event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player)) return;
        event.setCancelled(!clickable);

        items.entrySet().stream().filter(entry -> {
            /*var stack = item.getItem();
            if (stack.getType().equals(Material.PLAYER_HEAD)) {
                if (stack.getItemMeta() instanceof SkullMeta skullMeta) {
                    if (event.getCurrentItem().getItemMeta() instanceof SkullMeta currentItemMeta) {
                        if (skullMeta.hasOwner() && currentItemMeta.hasOwner()) {
                            if (skullMeta.getOwner().equals(currentItemMeta.getOwner())) {
                                if(skullMeta.displayName() == null && currentItemMeta.displayName() == null) {
                                    return (event.getRawSlot() == event.getSlot());
                                } else if(skullMeta.displayName() == null) {
                                    return false;
                                }
                                if (skullMeta.displayName().equals(currentItemMeta.displayName())) {
                                    return (event.getRawSlot() == event.getSlot());
                                }
                            }
                        }
                    }
                } else {
                    return stack.getItemMeta().displayName().equals(event.getCurrentItem().getItemMeta().displayName()) && stack.getType().equals(event.getCurrentItem().getType());
                }
            }
            return stack.equals(event.getCurrentItem());*/
            return entry.getKey() == event.getSlot();
        }).map(Map.Entry::getValue).findFirst().ifPresent(item -> {
            if (event.isLeftClick()) {
                item.click();
            } else if(event.getClick().equals(ClickType.MIDDLE)) {
                item.clickMid();
            } else {
                item.clickRight();
            }
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(getInventory())) {
            getItems().clear();
        }
    }
}
