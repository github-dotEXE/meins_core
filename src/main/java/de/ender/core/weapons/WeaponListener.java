package de.ender.core.weapons;

import de.ender.core.customItems.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class WeaponListener implements Listener {
    @EventHandler
    public void onRangedHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter() instanceof Player){
                Player player = (Player) projectile.getShooter();
                Weapon weapon = (Weapon) CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
                if(weapon == null) return;
                weapon.rangedEntityHit(player,event);
            }
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        event.getEntity();
        Projectile projectile = event.getEntity();
        if(projectile.getShooter() instanceof Player){
            Player player = (Player) projectile.getShooter();
            Weapon weapon = (Weapon) CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
            if(weapon == null) return;
            weapon.rangedHit(player,event);
        }
    }
}
