package de.ender.core.weapons;

import de.ender.core.customItems.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public interface Weapon {
    default WeaponHandle init(){
        WeaponHandle weaponHandle = new WeaponHandle(this);
        weaponHandle.init();
        return weaponHandle;
    }
    JavaPlugin getPlugin();
    default NamespacedKey getNamespacedKey(){
        return new NamespacedKey(getPlugin(),getClass().getName());
    }
    default UUID getUUID() {
        byte[] result = getNamespacedKey().toString().getBytes();
        return UUID.nameUUIDFromBytes(result);
    }

    String getName();
    ItemStack getItem();
    default ShapedRecipe getRecipe(){
        return new ShapedRecipe(getNamespacedKey(),CustomItems.getCustomItem(getUUID()).getItem());
    }
    long getReloadTime();
    void useEffects(Player player, CustomItems.UseType useType);
    ItemStack getAmmoItem(CustomItems.UseType useType);
    double getDamage();

    boolean hasRequirements(Player player);

    default void missingRequirements(Player player){
        player.playSound(player, Sound.ENTITY_VILLAGER_NO,1,1);
        player.sendActionBar(ChatColor.RED+"You don't fulfill the requirements to use this weapon!");
    }
    default void error(Player player){
        player.playSound(player,Sound.BLOCK_DECORATED_POT_BREAK,1,1);
        player.sendActionBar(ChatColor.RED+"Something went wrong whilst using your weapon!");
    }
    default void onCooldown(Player player){
        player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,1,1);
        player.sendActionBar(ChatColor.RED+"Your weapon is on cooldown!");
    }
    default void noAmmo(Player player){
        player.playSound(player,Sound.UI_BUTTON_CLICK,1,1); // test pitch and volume
        player.sendActionBar(ChatColor.GOLD+"You don't have enough ammo to use this weapon!");
    }

    default void rangedEntityHit(Player player, EntityDamageByEntityEvent event){
        event.setDamage(getDamage());
    }

    default void rangedHit(Player player, ProjectileHitEvent event){
        event.getEntity().remove();
    }
}