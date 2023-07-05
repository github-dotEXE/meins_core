package de.ender.core.weapons;

import de.ender.core.ItemBuilder;
import de.ender.core.customItems.CustomItem;
import de.ender.core.customItems.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WeaponHandle implements CustomItem {
    private final Weapon weapon;
    private final HashMap<Player,Long> lastFired = new HashMap<>();
    WeaponHandle(Weapon weapon){
        this.weapon = weapon;
        Weapons.register(this);
    }
    public Weapon getWeapon(){
        return weapon;
    }
    private boolean isOnCooldown(Player player){
        return !((System.currentTimeMillis()-lastFired.getOrDefault(player, 0L))>=weapon.getReloadTime());
    }
    private boolean hasAmmo(Player player, CustomItems.UseType useType) {
        ItemStack item = weapon.getAmmoItem(useType);
        return player.getInventory().containsAtLeast(item,item.getAmount());
    }
    private boolean checkRequirements(Player player, CustomItems.UseType useType){
        if(!Weapons.getCustomItem(player.getInventory().getItemInMainHand()).equals(this)) weapon.error(player);
        else if(!weapon.hasRequirements(player)) weapon.missingRequirements(player);
        else if(isOnCooldown(player)) weapon.onCooldown(player);
        else if(!hasAmmo(player,useType)) weapon.noAmmo(player);
        else return true;
        return false;
    }

    @Override
    public JavaPlugin getPlugin() {
        return weapon.getPlugin();
    }

    @Override
    public ItemStack getItemStack() {
        return weapon.getItem();
    }

    @Override
    public Recipe getRecipe() {
        return weapon.getRecipe();
    }

    @Override
    public String getName() {
        return weapon.getName();
    }

    public void use(@NotNull Player player, CustomItems.UseType useType){
        if(checkRequirements(player, useType)){
            weapon.useEffects(player,useType);
            lastFired.put(player,System.currentTimeMillis());
            removeAmmo(player,useType);
        }
    }
    public void removeAmmo(Player player, CustomItems.UseType useType){
        for (ItemStack item:
                player.getInventory()) {
            if(item!=null&&item.asOne().equals(weapon.getAmmoItem(useType).asOne())){
                item.setAmount(item.getAmount()-1);
                break;
            }
        }
    }

}