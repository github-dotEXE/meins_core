package de.ender.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemMeta;
    private static final MiniMessage minimessage = MiniMessage.miniMessage();


    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }
    public ItemBuilder(ItemStack item){
        this.item = item;
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        itemMeta.displayName(minimessage.deserialize(name));
        return this;
    }
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(String[] loresS) {
        ArrayList<Component> loresC = new ArrayList<>();
        for (String lore: loresS) loresC.add(minimessage.deserialize(lore));
        itemMeta.lore(loresC);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        List<Component> lores = new ArrayList<>();
        if(itemMeta.hasLore()) lores = itemMeta.lore();
        lores.add(minimessage.deserialize(lore));
        itemMeta.lore(lores);
        return this;
    }

    public ItemBuilder setLoreAt(int index, String lore) {
        List<Component> lores = new ArrayList<>();
        if(itemMeta.hasLore()) lores = itemMeta.lore();
        lores.add(index,minimessage.deserialize(lore));
        itemMeta.lore(lores);
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

