package de.ender.core;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CGUI {
    private final Inventory gui;

    public CGUI(int size,String title){
        gui = Bukkit.createInventory(null,size, Component.text(title));
    }
    public CGUI(int size,Component title){
        gui = Bukkit.createInventory(null,size, title);
    }
    public CGUI(Inventory inventory){
        gui = inventory;
    }
    public int getSize(){
        return gui.getSize();
    }
    public Inventory getGui(){
        return gui;
    }
    public void setGuiSpace(int index, ItemStack item){
        gui.setItem(index,item);
    }
    /**
     * x ∈ N₀<p>
     * y ∈ N₀<p>
     * coordinate system goes like:
     * <p>
     * O-------> x<p>
     * |<p>
     * |<p>
     * v<p>
     * y
     */
    public void setGuiSpace(int x,int y,ItemStack item){
        int index = y*9+x;
        setGuiSpace(index,item);
    }
    public void fillEmpty(ItemStack item){
        for(int i = 0; i<=getSize(); i++){
            if(gui.getItem(i) == null){
                gui.setItem(i,item);
            }
        }
    }
    public void clear(){
        for(ItemStack item : gui){
            gui.remove(item);
        }
    }
    public void fillAll(ItemStack item){
        for(int i = 0; i<=getSize(); i++){
            gui.setItem(i,item);
        }
    }
    public @Nullable ItemStack getGuiSpace(int index){
        return gui.getItem(index);
    }
    public @Nullable ItemStack getGuiSpace(int x,int y){
        int index = y*9+x;
        return getGuiSpace(index);
    }
    public @Nullable Material getGuiSpaceMaterial(int index){
        ItemStack item = getGuiSpace(index);
        if(item == null) return null;
        return item.getType();
    }
    public @Nullable Material getGuiSpaceMaterial(int x,int y) {
        int index = y * 9 + x;
        return getGuiSpaceMaterial(index);
    }
    public boolean isGuiSpaceMaterial(Material material,int index){
        return material == getGuiSpaceMaterial(index);
    }
    public boolean isGuiSpaceMaterial(Material material,int x,int y){
        return material == getGuiSpaceMaterial(x,y);
    }
    public boolean isGuiSpaceName(String name, int index){
        ItemStack item = getGuiSpace(index);
        if(item == null) return false;
        return Objects.equals(name, item.displayName().examinableName());
    }
    public boolean isGuiSpaceName(String name, int x, int y){
        int index = y * 9 + x;
        return isGuiSpaceName(name,index);
    }
    public boolean isGuiSpaceEmpty(int index){
        return getGuiSpace(index) == null;
    }
    public boolean isGuiSpaceEmpty(int x,int y){
        return getGuiSpace(x,y) == null;
    }
}
