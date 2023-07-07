package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CustomFoodItemListener implements Listener {
    @EventHandler
    public void onConsumption(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(event.getItem());
        if(customItem == null) return;
        event.setCancelled(true);
        if(customItem instanceof CustomFoodItem)
            ((CustomFoodItem)customItem).consume(player,event);
    }
}
