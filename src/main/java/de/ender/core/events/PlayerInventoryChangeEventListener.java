package de.ender.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryChangeEventListener implements Listener {
    @EventHandler
    public void inventoryDrag(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getOldCursor();
        PlayerInventoryChangeEvent playerInventoryChangeEvent = new PlayerInventoryChangeEvent(player,item);
        if(playerInventoryChangeEvent.isCancelled()) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
    @EventHandler
    public void inventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        PlayerInventoryChangeEvent playerInventoryChangeEvent = new PlayerInventoryChangeEvent(player,item);
        if(playerInventoryChangeEvent.isCancelled()) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
    @EventHandler
    public void inventoryPickup(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();
        PlayerInventoryChangeEvent playerInventoryChangeEvent = new PlayerInventoryChangeEvent(player,item);
        if(playerInventoryChangeEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void inventoryConsume(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        PlayerInventoryChangeEvent playerInventoryChangeEvent = new PlayerInventoryChangeEvent(player,item);
        if(playerInventoryChangeEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void inventoryDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        PlayerInventoryChangeEvent playerInventoryChangeEvent = new PlayerInventoryChangeEvent(player,item);
        if(playerInventoryChangeEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
