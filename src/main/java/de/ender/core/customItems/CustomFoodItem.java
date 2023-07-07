package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public abstract class CustomFoodItem extends CustomItem {
    protected void consume(Player player, PlayerItemConsumeEvent event){
        player.setFoodLevel(Math.min(20,getHunger()));
        player.setFoodLevel(Math.min(20,getSaturation()));
    }
    protected abstract int getHunger();
    protected abstract int getSaturation();
}
