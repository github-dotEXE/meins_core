package de.ender.core.weapons;

import de.ender.core.customItems.CustomItems;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Weapons extends CustomItems implements Listener {

    @EventHandler
    public void onRangedHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter() instanceof Player){
                Player player = (Player) projectile.getShooter();
                WeaponHandle weaponHandle = (WeaponHandle) getCustomItem(player.getInventory().getItemInMainHand());
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
            WeaponHandle weaponHandle = (WeaponHandle) getCustomItem(player.getInventory().getItemInMainHand());
            if(weaponHandle == null) return;
            weaponHandle.getWeapon().rangedHit(player,event);
        }
    }
}