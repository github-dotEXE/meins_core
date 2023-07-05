package de.ender.core.weapons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Weapons implements Listener {
    private static final HashMap<String, WeaponHandle> weaponNames = new HashMap<>();
    private static final HashMap<UUID, WeaponHandle> weaponItems = new HashMap<>();
    public enum UseType{
        USE,
        RIGHT_CLICK_ENTITY,
        HURT_ENTITY,
        LEFT_CLICK
    }
    public static void register(WeaponHandle weaponHandle){
        weaponNames.put(weaponHandle.getWeapon().getName(),weaponHandle);
        weaponItems.put(weaponHandle.getWeapon().getUUID(), weaponHandle);

        try{
            weaponHandle.getWeapon().getRecipe().getShape();
            Bukkit.addRecipe(weaponHandle.getWeapon().getRecipe());
        } catch (NullPointerException e){
            return;
        }
    }
    public static WeaponHandle getWeapon(String name){
        return weaponNames.getOrDefault(name,null);
    }
    public static WeaponHandle getWeapon(ItemStack item){
        if(item == null) return null;
        @Nullable List<String> lore = item.getLore();
        if(lore==null) return null;
        String firstLore = ChatColor.stripColor(lore.get(lore.size()-1)).replaceFirst("ItemID: ","");
        try {
            return getWeapon(UUID.fromString(firstLore));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static WeaponHandle getWeapon(UUID uuid){
        return weaponItems.getOrDefault( uuid ,null);
    }
    public static Set<String> getWeaponNames(){
        return weaponNames.keySet();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        WeaponHandle weaponHandle = getWeapon(event.getItem());
        if(weaponHandle == null) return;
        event.setCancelled(true);
        if(event.getAction().isRightClick()) weaponHandle.use(player, UseType.USE);
        else if(event.getAction().isLeftClick()) weaponHandle.use(player, UseType.LEFT_CLICK);
    }
    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        WeaponHandle weaponHandle = getWeapon(player.getInventory().getItemInMainHand());
        if(weaponHandle == null) return;
        event.setCancelled(true);
        weaponHandle.use(player, UseType.RIGHT_CLICK_ENTITY);
    }
    @EventHandler
    public void onHurtEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            WeaponHandle weaponHandle = getWeapon(player.getInventory().getItemInMainHand());
            if(weaponHandle == null) return;
            event.setCancelled(true);
            weaponHandle.use(player, UseType.HURT_ENTITY);
        }
    }

    @EventHandler
    public void onRangedHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter() instanceof Player){
                Player player = (Player) projectile.getShooter();
                WeaponHandle weaponHandle = getWeapon(player.getInventory().getItemInMainHand());
                if(weaponHandle == null) return;
                weaponHandle.getWeapon().rangedEntityHit(player,event);
            }
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        event.getEntity();
        Projectile projectile = event.getEntity();
        if(projectile.getShooter() instanceof Player){
            Player player = (Player) projectile.getShooter();
            WeaponHandle weaponHandle = getWeapon(player.getInventory().getItemInMainHand());
            if(weaponHandle == null) return;
            weaponHandle.getWeapon().rangedHit(player,event);
        }
    }
}
