package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CustomUsableItemListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(event.getItem());
        if(customItem == null) return;
        if(!(customItem instanceof CustomUseableItem)) return;
        event.setCancelled(true);
        if(event.getAction().isRightClick()) ((CustomUseableItem)customItem).use(player, CustomItem.UseType.USE);
        else if(event.getAction().isLeftClick()) ((CustomUseableItem)customItem).use(player, CustomItem.UseType.LEFT_CLICK);
    }
    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
        if(customItem == null) return;
        if(!(customItem instanceof CustomUseableItem)) return;
        event.setCancelled(true);
        ((CustomUseableItem)customItem).use(player, CustomItem.UseType.RIGHT_CLICK_ENTITY);
    }
    @EventHandler
    public void onHurtEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            CustomItem customItem = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
            if(customItem == null) return;
            if(!(customItem instanceof CustomUseableItem)) return;
            event.setCancelled(true);
            ((CustomUseableItem)customItem).use(player, CustomItem.UseType.HURT_ENTITY);
        }
    }
    @EventHandler
    public void onSwitchHotbar(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        CustomItem itemP = CustomItem.getCustomItem(player.getInventory().getItem(event.getPreviousSlot()));
        CustomItem itemN = CustomItem.getCustomItem(player.getInventory().getItem(event.getNewSlot()));
        if (itemP instanceof CustomUseableItem) ((CustomUseableItem)itemP).switchFromSlot(player, event);
        if (itemN instanceof CustomUseableItem) ((CustomUseableItem)itemN).switchToSlot(player, event);
    }
}
