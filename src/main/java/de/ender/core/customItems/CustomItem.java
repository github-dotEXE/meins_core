package de.ender.core.customItems;

import de.ender.core.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public interface CustomItem {
    default void init(){
        Bukkit.addRecipe(getRecipe());
        CustomItems.register(this);
    }

    default ItemStack getItem(){
        return new ItemBuilder(getItemStack()).addLore(ChatColor.DARK_GRAY+"ItemID: "+getUUID().toString()).build();
    }

    default UUID getUUID() {
        byte[] result = getNamespacedKey().toString().getBytes();
        return UUID.nameUUIDFromBytes(result);
    }
    default NamespacedKey getNamespacedKey(){
        return new NamespacedKey(getPlugin(),getClass().getName());
    }

    JavaPlugin getPlugin();
    ItemStack getItemStack();
    Recipe getRecipe();
    String getName();

    default void use(Player player, CustomItems.UseType use){

    }
}
