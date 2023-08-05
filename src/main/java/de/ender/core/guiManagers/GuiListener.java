package de.ender.core.guiManagers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {


    @EventHandler
    public void onInventoryInteractEvent(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();
        if(item == null) return;
        Player player = (Player) event.getWhoClicked();
        if(!CustomGUI.playerInGUI(player)) return;
        //String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        CustomGUI cgui = CustomGUI.getGUI(player);
        if(cgui == null) return;
        cgui.triggerInv(item,event);
    }
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(!CustomGUI.playerInGUI(player)) return;
        //String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        CustomGUI cgui = CustomGUI.getGUI(player);
        if(cgui == null) return;
        cgui.cancel(event);
    }
}
