package de.ender.core.customItems;

import de.ender.core.weapons.WeaponHandle;
import de.ender.core.weapons.Weapons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CustomItems implements Listener {
    public enum UseType{
        USE,
        RIGHT_CLICK_ENTITY,
        HURT_ENTITY,
        LEFT_CLICK
    }
    private static final HashMap<String, CustomItem> names = new HashMap<>();
    private static final HashMap<UUID, CustomItem> uuids = new HashMap<>();

    public static void register(CustomItem customItem){
        names.put(customItem.getName(),customItem);
        uuids.put(customItem.getUUID(), customItem);
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
    public static Set<String> getNameSet(){
        return names.keySet();
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = getCustomItem(event.getItem());
        if(customItem == null) return;
        event.setCancelled(true);
        if(event.getAction().isRightClick()) customItem.use(player, UseType.USE);
        else if(event.getAction().isLeftClick()) customItem.use(player, UseType.LEFT_CLICK);
    }
    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        CustomItem customItem = getCustomItem(player.getInventory().getItem(event.getHand()));
        if(customItem == null) return;
        event.setCancelled(true);
        customItem.use(player, UseType.RIGHT_CLICK_ENTITY);
    }
    @EventHandler
    public void onHurtEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            CustomItem customItem = getCustomItem(player.getInventory().getItemInMainHand());
            if(customItem == null) return;
            event.setCancelled(true);
            customItem.use(player, UseType.HURT_ENTITY);
        }
    }
}
