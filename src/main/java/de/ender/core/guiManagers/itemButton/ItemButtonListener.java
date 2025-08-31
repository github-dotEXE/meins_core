package de.ender.core.guiManagers.itemButton;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemButtonListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        ClickType clickType = event.getClick();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();

        ItemButton itemButton = ItemButtons.getItemButton(item,inventory,slot);
        if(itemButton == null) return;

        switch (clickType) {
            case LEFT:
                itemButton.onLeftClick();
                break;
            case RIGHT:
                itemButton.onRightClick();
                break;
            case MIDDLE:
                itemButton.onMiddleClick();
                break;
            case SHIFT_LEFT:
                itemButton.onShiftLeftClick();
                break;
            case SHIFT_RIGHT:
                itemButton.onShiftRightClick();
                break;
            case DROP:
                itemButton.onDrop();
                break;
        }

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
    }
}
