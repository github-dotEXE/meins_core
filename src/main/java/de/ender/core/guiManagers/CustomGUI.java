package de.ender.core.guiManagers;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class CustomGUI {
    private final Player player;
    private final String name;
    public CustomGUI(Player player, String name) {
        this.player = player;
        this.name = name;
        GuiListener.registerGUI(this);
    }
    public Player getPlayer(){
        return player;
    }
    public String getName(){
        return name;
    }
    public void triggerInv(ItemStack item, InventoryClickEvent event){
    }
    public void cancel(InventoryCloseEvent event){
    }
}
