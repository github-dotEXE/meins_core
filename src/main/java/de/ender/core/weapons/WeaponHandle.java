package de.ender.core.weapons;

import de.ender.core.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WeaponHandle {
    private final Weapon weapon;
    private final HashMap<Player,Long> lastFired = new HashMap<>();
    WeaponHandle(Weapon weapon){
        this.weapon = weapon;
        Weapons.register(this);
    }
    public Weapon getWeapon(){
        return weapon;
    }
    public ItemStack getWeaponItem(){
        return new ItemBuilder(weapon.getItem()).addLore(ChatColor.DARK_GRAY+"ItemID: "+weapon.getUUID().toString()).build();
    }
    private boolean isOnCooldown(Player player){
        return !((System.currentTimeMillis()-lastFired.getOrDefault(player, 0L))>=weapon.getReloadTime());
    }
    private boolean hasAmmo(Player player, Weapons.UseType useType) {
        ItemStack item = weapon.getAmmoItem(useType);
        return player.getInventory().containsAtLeast(item,item.getAmount());
    }
    private boolean checkRequirements(Player player, Weapons.UseType useType){
        if(!Weapons.getWeapon(player.getInventory().getItemInMainHand()).equals(this)) weapon.error(player);
        else if(!weapon.hasRequirements(player)) weapon.missingRequirements(player);
        else if(isOnCooldown(player)) weapon.onCooldown(player);
        else if(!hasAmmo(player,useType)) weapon.noAmmo(player);
        else return true;
        return false;
    }
    public void use(@NotNull Player player, Weapons.UseType useType){
        if(checkRequirements(player, useType)){
            weapon.useEffects(player,useType);
            lastFired.put(player,System.currentTimeMillis());
            removeAmmo(player,useType);
        }
    }
    public void removeAmmo(Player player, Weapons.UseType useType){
        for (ItemStack item:
             player.getInventory()) {
            if(item!=null&&item.asOne().equals(weapon.getAmmoItem(useType).asOne())){
                item.setAmount(item.getAmount()-1);
                break;
            }
        }
    }

}
