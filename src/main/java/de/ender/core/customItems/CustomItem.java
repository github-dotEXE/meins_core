package de.ender.core.customItems;

import de.ender.core.ItemBuilder;
import de.ender.core.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class CustomItem {
    private final String name;
    private final JavaPlugin plugin;

    public CustomItem(String name, JavaPlugin plugin){
        this.name = name;
        this.plugin = plugin;
    }
    protected enum UseType{
        USE,
        RIGHT_CLICK_ENTITY,
        HURT_ENTITY,
        LEFT_CLICK,
    }
    private static final HashMap<String, CustomItem> names = new HashMap<>();
    private static final HashMap<UUID, CustomItem> uuids = new HashMap<>();

    protected void register(){
        names.put(getName(),this);
        uuids.put(getUUID(), this);
    }
    public static CustomItem getCustomItem(String name){
        return names.getOrDefault(name,null);
    }
    public static CustomItem getCustomItem(ItemStack item){
        if(item == null) return null;
        @Nullable List<String> lore = item.getLore();
        if(lore==null) return null;
        String firstLore = ChatColor.stripColor(lore.get(lore.size()-1)).replaceFirst("ItemID: ","");
        try {
            return getCustomItem(UUID.fromString(firstLore));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static CustomItem getCustomItem(UUID uuid){
        return uuids.getOrDefault( uuid ,null);
    }
    public static ArrayList<String> getNames(){
        return new ArrayList<>(names.keySet());
    }
    protected void removeItem(Player player,ItemStack item){
        for (ItemStack items: player.getInventory()) {
            if(items!=null&&items.asOne().equals(item.asOne())){
                items.setAmount(items.getAmount()-item.getAmount());
                break;
            }
        }
    }
    
    public void init(){
        Bukkit.removeRecipe(getNamespacedKey());
        register();
        getCustomItem(getUUID());
        Recipe recipe = getRecipe();
        try{
            if(!Bukkit.addRecipe(recipe)) Log.warn("Recipe couldn't be added");
        } catch(NullPointerException e) {
            Log.info("Custom item "+getName()+" has no recipe!");
        }
    }

    public ItemStack getItem(){
        return new ItemBuilder(getItemStack()).addLore(ChatColor.DARK_GRAY+"ItemID: "+getUUID().toString()).build().asOne();
    }
    protected abstract ItemStack getItemStack();

    public UUID getUUID() {
        byte[] result = getNamespacedKey().toString().getBytes();
        return UUID.nameUUIDFromBytes(result);
    }
    public NamespacedKey getNamespacedKey(){
        return new NamespacedKey(getPlugin(),getName());
    }

    public JavaPlugin getPlugin(){
        return this.plugin;
    }
    protected abstract Recipe getRecipe();
    public String getName(){
        return name;
    }
}
