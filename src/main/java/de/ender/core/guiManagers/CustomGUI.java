package de.ender.core.guiManagers;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class CustomGUI {
    private static final HashMap<Player, CustomGUI> registeredGuiInstances = new HashMap<>();
    public static void registerGUI(CustomGUI customGUI){
        registeredGuiInstances.put(customGUI.getPlayer(),customGUI);
    }
    public static void unregisterGUI(CustomGUI customGUI){
        registeredGuiInstances.remove(customGUI.getPlayer());
    }
    public static CustomGUI getGUI(Player player){
        return registeredGuiInstances.get(player);
    }
    public static boolean playerInGUI(Player player){
        return registeredGuiInstances.containsKey(player);
    }
    private final Player player;
    private final String name;
    public CustomGUI(Player player, String name) {
        this.player = player;
        this.name = name;
        registerGUI(this);
    }
    public Player getPlayer(){
        return player;
    }
    public String getName(){
        return name;
    }
    public abstract void triggerInv(ItemStack item, InventoryClickEvent event);
    public abstract void cancel(InventoryCloseEvent event);
}
