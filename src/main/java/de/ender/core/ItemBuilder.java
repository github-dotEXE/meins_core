package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;


    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }
    public ItemBuilder(ItemStack item){
        this.item = item;
        itemMeta = item.getItemMeta();
    }

    @Deprecated
    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    @Deprecated
    public ItemBuilder setLore(String[] lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    @Deprecated
    public ItemBuilder addLore(String lore) {
        List<String> lorebefore = itemMeta.getLore();

        List<String> newLore;
        if(lorebefore != null) newLore = new ArrayList<>(lorebefore);
        else newLore = new ArrayList<>();

        newLore.add(lore);
        itemMeta.setLore(newLore);
        return this;
    }
    @Deprecated
    public ItemBuilder setLoreAt(int index, String lore) {
        List<String> lorebefore = itemMeta.getLore();

        List<String> newLore;
        if(lorebefore != null) newLore = new ArrayList<>(lorebefore);
        else newLore = new ArrayList<>();

        newLore.add(index,lore);
        itemMeta.setLore(newLore);
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
    public ItemBuilder addEnchantmentGlint(){
        ItemMeta itemMeta = item.getItemMeta();
        Glow glow = new Glow();
        itemMeta.addEnchant(glow, 1, true);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }

}

