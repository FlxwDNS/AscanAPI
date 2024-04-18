package de.flxwdev.ascan.inventory.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class ItemBuilder extends ItemStack {
    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public ItemBuilder withName(String name) {
        var meta = getItemMeta();
        meta.displayName(Component.text("§r§7" + name).decoration(TextDecoration.ITALIC, false));
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(List<String> lines) {
        var meta = getItemMeta();
        meta.lore(lines.stream().map(it -> Component.text("§r§7" + it).decoration(TextDecoration.ITALIC, false)).toList());
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        setAmount(amount);
        return this;
    }

    public ItemBuilder withGlow() {
        addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        var meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder withFlag(ItemFlag itemFlag) {
        var meta = getItemMeta();
        meta.addItemFlags(itemFlag);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder withFlags(ItemFlag... itemFlags) {
        for (ItemFlag itemFlag : itemFlags) {
            withFlag(itemFlag);
        }
        return this;
    }

    public ItemBuilder leatherColor(Color color) {
        var meta = (LeatherArmorMeta) getItemMeta();
        meta.setColor(color);
        setItemMeta(meta);
        return this;
    }


    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }
}
