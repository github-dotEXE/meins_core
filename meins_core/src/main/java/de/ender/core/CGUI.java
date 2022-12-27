package de.ender.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CGUI {

    private TextComponent nameComp;
    private int invSize;
    private Inventory gui;

    public CGUI (int size, String name) {
        nameComp = Component.text(name);
        invSize = size;
        gui = Bukkit.createInventory(null, invSize, nameComp);
    }

    public CGUI addItem(int row, int col, ItemStack item) {
        gui.setItem((9*row+col), item);
        return this;
    }

    public CGUI addItem(int locationIndex, ItemStack item) {
        gui.setItem(locationIndex, item);
        return this;
    }

    public CGUI addItems(int[] locationIndexs, ItemStack[] items) {
        for(int i = 0; i <= locationIndexs.length; i = i+1) gui.setItem(locationIndexs[i], items[i]);
        return this;
    }

    public CGUI addItems(int[] locationIndexs, ItemStack item) {
        for(int i = 0; i <= locationIndexs.length; i = i+1) gui.setItem(locationIndexs[i], item);
        return this;
    }

    public CGUI fillItem(ItemStack item) {
        while(this.gui.firstEmpty() != -1){
            gui.setItem(this.gui.firstEmpty(), item);
        }
        return this;
    }

    public Inventory build(){
        return this.gui;
    }

}
