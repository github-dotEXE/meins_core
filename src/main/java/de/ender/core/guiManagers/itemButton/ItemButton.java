package de.ender.core.guiManagers.itemButton;

import de.ender.core.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ItemButton {
    private ItemStack item;
    private Inventory inventory;
    private int slot;

    public ItemButton(Material material, int amount, Component name){
        this.item = new ItemBuilder(material,amount).setName(name).build();
    }
    public ItemButton(ItemStack item, Component name){
        this.item = new ItemBuilder(item).setName(name).build();
    }
    public void putInInv(Inventory inventory,int slot){
        this.inventory = inventory;
        this.slot = slot;
        reloadButton();
    }
    protected void reloadButton(){
        inventory.setItem(slot,item);
    }
    public void changeItem(ItemStack item){
        this.item = item;
        reloadButton();
    }
    public void changeMaterial(Material material){
        this.item = new ItemBuilder(this.item).setMaterial(material).build();
        reloadButton();
    }
    public void changeAmount(int amount){
        this.item = new ItemBuilder(this.item).setAmount(amount).build();
        reloadButton();
    }
    public abstract void onLeftClick();
    public abstract void onRightClick();
    public abstract void onMiddleClick();
    public abstract void onShiftLeftClick();
    public abstract void onShiftRightClick();
    public abstract void onDrop();

    boolean isThis(ItemStack item, Inventory inventory, int slot){
        return item.equals(this.item)&&
                inventory.equals(this.inventory)&&
                slot==this.slot;
    }
}
