package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CustomItemListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(event.getItem());
        if(customItem == null) return;
        event.setCancelled(true);
        if(event.getAction().isRightClick()) customItem.use(player, CustomItem.UseType.USE);
        else if(event.getAction().isLeftClick()) customItem.use(player, CustomItem.UseType.LEFT_CLICK);
    }
    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
        if(customItem == null) return;
        event.setCancelled(true);
        customItem.use(player, CustomItem.UseType.RIGHT_CLICK_ENTITY);
    }
    @EventHandler
    public void onHurtEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            CustomItem customItem = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
            if(customItem == null) return;
            event.setCancelled(true);
            customItem.use(player, CustomItem.UseType.HURT_ENTITY);
        }
    }
    @EventHandler
    public void onConsumption(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = CustomItem.getCustomItem(event.getItem());
        if(customItem == null) return;
        event.setCancelled(true);
        customItem.use(player, CustomItem.UseType.CONSUME);
    }
    @EventHandler
    public void onSwitchHotbar(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        CustomItem itemP = CustomItem.getCustomItem(player.getInventory().getItem(event.getPreviousSlot()));
        CustomItem itemN = CustomItem.getCustomItem(player.getInventory().getItem(event.getNewSlot()));
        if (itemP != null) itemP.switchFromSlot(player, event);
        if (itemN != null) itemN.switchToSlot(player, event);
    }
}
