package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;


    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }

    @Deprecated
    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    @Deprecated
    public ItemBuilder setLore(String[] lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    @Deprecated
    public ItemBuilder addLore(int index, String lore) {
        itemMeta.getLore().add(lore);
        return this;
    }
    @Deprecated
    public ItemBuilder setLoreAt(int index, String lore) {
        itemMeta.getLore().add(index,lore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }
    public ItemBuilder addMaxEnchantment(Enchantment enchantment) {
        item.addEnchantment(enchantment, enchantment.getMaxLevel());
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }

}

