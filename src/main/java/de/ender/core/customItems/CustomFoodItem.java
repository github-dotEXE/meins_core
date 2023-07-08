package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomFoodItem extends CustomItem {
    private final int hunger;
    private final float saturation;
    public CustomFoodItem(String name, ItemStack item, JavaPlugin plugin, int hunger, float saturation) {
        super(name, item, plugin);
        this.hunger = hunger;
        this.saturation = saturation;
    }

    protected void consume(Player player, PlayerItemConsumeEvent event){
        player.setFoodLevel(Math.min(20,hunger+player.getFoodLevel()));
        player.setSaturation(saturation+player.getSaturation());
        player.getInventory().setItem(event.getHand(),event.getItem().asQuantity(event.getItem().getAmount()-1));
    }
}
