package de.ender.core.guiManagers.itemButton;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemButtons {
    private static final ArrayList<ItemButton> itemButtons = new ArrayList<>();
    static void registerButton(ItemButton itemButton){
        itemButtons.add(itemButton);
    }
    public static boolean isItemButton(ItemStack item, Inventory inventory, int slot){
        return getItemButton(item,inventory,slot) != null;
    }
    public static ItemButton getItemButton(ItemStack item, Inventory inventory, int slot){
        for (ItemButton itemButton : itemButtons) if (itemButton.isThis(item, inventory, slot)) return itemButton;
        return null;
    }
}
