package de.flxwdev.ascan.inventory.item;

import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

@Accessors(fluent = true)
public class ItemView extends ItemStack {
    public ItemView(Material material) {
        super(material);
    }

    public ItemView(ItemStack itemStack) {
        super(itemStack);
    }

    public ItemView withName(String name) {
        var meta = getItemMeta();
        meta.displayName(Component.text("§r§7" + name).decoration(TextDecoration.ITALIC, false));
        setItemMeta(meta);
        return this;
    }

    public ItemView withLore(List<String> lines) {
        var meta = getItemMeta();
        meta.lore(lines.stream().map(it -> Component.text("§r§7" + it).decoration(TextDecoration.ITALIC, false)).toList());
        setItemMeta(meta);
        return this;
    }

    public ItemView withAmount(int amount) {
        setAmount(amount);
        return this;
    }

    public ItemView withGlow() {
        addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        var meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
        return this;
    }

    public ItemView enchant(Enchantment enchantment, int level) {
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemView withFlag(ItemFlag itemFlag) {
        var meta = getItemMeta();
        meta.addItemFlags(itemFlag);
        setItemMeta(meta);
        return this;
    }

    public ItemView withFlags(ItemFlag... itemFlags) {
        for (ItemFlag itemFlag : itemFlags) {
            withFlag(itemFlag);
        }
        return this;
    }

    public ItemView leatherColor(Color color) {
        var meta = (LeatherArmorMeta) getItemMeta();
        meta.setColor(color);
        setItemMeta(meta);
        return this;
    }


    public static ItemView of(Material material) {
        return new ItemView(material);
    }

    public static ItemView of(ItemStack itemStack) {
        return new ItemView(itemStack);
    }
}
