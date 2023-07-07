package de.ender.core.guiManagers;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiListener implements Listener {
    private static final HashMap<Player, CustomGUI> registeredGuiInstances = new HashMap<>();
    public static void registerGUI(CustomGUI customGUI){
        registeredGuiInstances.put(customGUI.getPlayer(),customGUI);
    }
    public static void unregisterGUI(CustomGUI customGUI){
        registeredGuiInstances.remove(customGUI.getPlayer());
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();
        if(item == null) return;
        Player player = (Player) event.getWhoClicked();
        if(!registeredGuiInstances.containsKey(player)) return;
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        for (Map.Entry<Player, CustomGUI> entry:
             registeredGuiInstances.entrySet()) {
            CustomGUI cgui = entry.getValue();
            Player cguiPlayer = entry.getKey();
            if(player.equals(cguiPlayer)) {
                cgui.triggerInv(item,event);
                break;
            }
        }
    }
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(!registeredGuiInstances.containsKey(player)) return;
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        for (Map.Entry<Player, CustomGUI> entry:
                registeredGuiInstances.entrySet()) {
            CustomGUI cgui = entry.getValue();
            Player cguiPlayer = entry.getKey();
            if(player.equals(cguiPlayer)) {
                cgui.cancel(event);
                break;
            }
        }
    }
}
