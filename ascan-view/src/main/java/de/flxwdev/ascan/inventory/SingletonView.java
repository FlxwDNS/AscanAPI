package de.flxwdev.ascan.inventory;

import de.flxwdev.ascan.AscanAPI;
import de.flxwdev.ascan.inventory.item.InteractItem;
import de.flxwdev.ascan.inventory.item.ItemView;
import dev.dbassett.skullcreator.SkullCreator;
import lombok.Getter;
import lombok.experimental.Accessors;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Accessors(fluent = true)
public abstract class SingletonView implements Listener {
    @Getter
    private final Inventory inventory;
    @Getter
    private final Map<Integer, InteractItem> items;
    private final Boolean clickable;
    private final int rows;
    private long animation;

    @Getter
    private final Player player;

    @Getter
    private Material placeHolder;

    public SingletonView(Player player, Component name, int rows, boolean clickable) {
        this(player, name, rows, clickable, false);
    }

    public SingletonView(Player player, Component name, int rows, boolean clickable, boolean open) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, rows * 9, name.append(Component.text(" §8(§7" + player.getName() + "§8)")));
        this.items = new HashMap<>();
        this.clickable = clickable;
        this.rows = rows;
        this.animation = 0;
        this.placeHolder = AscanAPI.getConfig().placeHolder();

        AscanAPI.getInstance().getServer().getPluginManager().registerEvents(this, AscanAPI.getInstance());

        if(open) {
            open(player);
        }
    }

    public void animation(long delay) {
        this.animation = delay;
    }

    public void customPlaceholder(Material placeholder) {
        this.placeHolder = placeholder;
    }

    public void backPage(Class<? extends SingletonView> inventory) {
        var item = new InteractItem(ItemView.of(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc")).name("§8» §6Zurück"), () -> {
            try {
                inventory.getConstructors()[0].newInstance(player);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        if (itemStack(rows, 4).getType().equals(placeHolder)) {
            item(rows, 4, item);
        } else if (itemStack(rows, 5).getType().equals(placeHolder)) {
            item(rows, 3, item);
            this.inventory.setItem(((rows - 1) * 9) + 5, itemStack(rows, 4));
            item(rows, 4, ItemView.of(placeHolder).name("§7 "));
        } else {
            item(rows, 3, item);
        }
    }

    public ItemStack itemStack(int row, int slot) {
        return inventory.getItem(((row - 1) * 9) + slot);
    }

    public void item(ItemStack item) {
        inventory.addItem(item);
    }

    public void item(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        items.remove(slot);
    }

    public void item(int row, int slot, ItemStack item) {
        inventory.setItem(((row - 1) * 9) + slot, item);
        items.remove(((row - 1) * 9) + slot);
    }

    public void open(Player player) {
        if (!(player.getOpenInventory().getType().equals(InventoryType.CHEST))) {
            //player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1f, 1f);
        } else {
            //player.playSound(player.getLocation(), Sound.BLOCK_PACKED_MUD_PLACE, 1f, 1f);
        }
        if(animation > 0) {
            System.out.println("ANIMATION START");
            Map<ItemStack, Integer> contents = new HashMap<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                if(inventory.getItem(i) == null) continue;
                contents.put(inventory.getItem(i), i);
            }

            inventory.clear();
            new BukkitRunnable() {
                int count = 0;

                @Override
                public void run() {
                    if(count >= contents.size()) {
                        cancel();
                        return;
                    }
                    var item = contents.entrySet().stream().toList().get(count);
                    inventory.setItem(item.getValue(), item.getKey());

                    count++;
                }
            }.runTaskTimer(AscanAPI.getInstance(), 0, animation);
            return;
        }

        player.openInventory(inventory);
    }

    public void item(int slot, InteractItem interactItem) {
        inventory.setItem(slot, interactItem.item());
        items.put(slot, interactItem);
    }

    public void item(int row, int slot, InteractItem interactItem) {
        inventory.setItem(((row - 1) * 9) + slot, interactItem.item());
        items.put(((row - 1) * 9) + slot, interactItem);
    }

    public int item(InteractItem clickableItem) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, clickableItem.item());
                items.put(i, clickableItem);
                return i;
            }
        }
        return 0;
    }

    public void placeHolder(int row) {
        for (int i = 0; i < 9; i++) placeHolder(row, i);
    }

    public void placeHolder(int row, int slot) {
        item(row, slot, ItemView.of(new ItemStack(placeHolder)).name("§7 "));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        if (event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player) || event.getClickedInventory() == null) return;
        event.setCancelled(!clickable);

        items.entrySet().stream().filter(entry -> entry.getKey() == event.getSlot() && event.getClickedInventory().equals(inventory())).map(Map.Entry::getValue).findFirst().ifPresent(item -> {
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
        if (event.getInventory().equals(inventory())) {
            items().clear();
        }
    }
}
