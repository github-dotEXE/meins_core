package de.ender.core.weapons;

import de.ender.core.customItems.CustomItem;
import de.ender.core.customItems.CustomUseableItem;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public abstract class Weapon extends CustomUseableItem {

    private final HashMap<Player,Long> lastFired = new HashMap<>();
    private final boolean zoomable;
    private final float reloadTime;
    private final ItemStack ammoItem;
    private final double damage;
    private final boolean requireZoom;
    private final boolean reloadOnZoom;
    private final Sound fireingSound;
    private final float fireingSoundVolume;
    private final float fireingSoundPitch;

    public Weapon(String name, ItemStack item,ItemStack ammoItem, JavaPlugin plugin,float reloadTime,double damage,
                  boolean zoomable,boolean requireZoom,boolean reloadOnZoom,
                  Sound fireingSound,float fireingSoundVolume,float fireingSoundPitch) {
        super(name, item, plugin);
        this.reloadTime = reloadTime;
        this.zoomable = zoomable;
        this.ammoItem = ammoItem;
        this.damage = damage;
        this.requireZoom = requireZoom;
        this.reloadOnZoom = reloadOnZoom;
        this.fireingSound = fireingSound;
        this.fireingSoundVolume = fireingSoundVolume;
        this.fireingSoundPitch = fireingSoundPitch;
    }

    private boolean isOnCooldown(Player player){
        return !((System.currentTimeMillis()-lastFired.getOrDefault(player, 0L))>=reloadTime);
    }
    private boolean hasAmmo(Player player) {
        ItemStack item = ammoItem.asOne();
        return player.getInventory().containsAtLeast(item,item.getAmount());
    }
    private boolean checkRequirements(Player player){
        if(!CustomItem.getCustomItem(player.getInventory().getItemInMainHand()).equals(this)) error(player);
        else if(!hasRequirements(player)) missingRequirements(player);
        else if(requireZoom&&!isZoomed(player)) notZoomed(player);
        else if(isOnCooldown(player)) onCooldown(player);
        else if(!hasAmmo(player)) noAmmo(player);
        else return true;
        return false;
    }

    public void use(@NotNull Player player, UseType useType){
        if(zoomable&& Arrays.asList(UseType.USE,UseType.RIGHT_CLICK_ENTITY).contains(useType)){
            changeZoom(player);
            if(reloadOnZoom&&requireZoom&&isOnCooldown(player)&&isZoomed(player)) lastFired.put(player, 0L);
            zoomEffects(player);
        } else if(checkRequirements(player)){
            useEffects(player,useType);
            player.playSound(player,fireingSound,fireingSoundVolume,fireingSoundPitch);
            lastFired.put(player,System.currentTimeMillis());
            removeItem(player,getItem());
        }
    }

    protected boolean isZoomed(Player player){
        return player.getWalkSpeed() == -1F;
    }

    protected void changeZoom(Player player){
        if(!isZoomed(player)) startZooming(player);
        else stopZooming(player);
    }
    protected void stopZooming(Player player){
        player.setWalkSpeed(0.2F);
    }
    protected void startZooming(Player player){
        player.setWalkSpeed(-1F);
    }

    public NamespacedKey getNamespacedKey(){
        return new NamespacedKey(getPlugin(),getClass().getName());
    }
    public UUID getUUID() {
        byte[] result = getNamespacedKey().toString().getBytes();
        return UUID.nameUUIDFromBytes(result);
    }

    protected void zoomEffects(Player player) {
        player.playSound(player,Sound.ITEM_SPYGLASS_USE,1,1);
    }
    public ShapedRecipe getRecipe(){
        return new ShapedRecipe(getNamespacedKey(),getCustomItem(getUUID()).getItem());
    }
    public abstract void useEffects(Player player, UseType useType);
    public abstract boolean hasRequirements(Player player);

    protected void missingRequirements(Player player){
        player.playSound(player, Sound.ENTITY_VILLAGER_NO,1,1);
        player.sendActionBar(ChatColor.RED+"You don't fulfill the requirements to use this weapon!");
    }
    protected void error(Player player){
        player.playSound(player,Sound.BLOCK_DECORATED_POT_BREAK,1,1);
        player.sendActionBar(ChatColor.RED+"Something went wrong whilst using your weapon!");
    }
    protected void onCooldown(Player player){
        player.playSound(player,Sound.ENTITY_ENDERMAN_TELEPORT,1,1);
        player.sendActionBar(ChatColor.RED+"Your weapon is on cooldown!");
    }
    protected void noAmmo(Player player){
        player.playSound(player,Sound.UI_BUTTON_CLICK,1,1); // test pitch and volume
        player.sendActionBar(ChatColor.GOLD+"You don't have enough ammo to use this weapon!");
    }

    @Override
    protected void switchFromSlot(Player player, PlayerItemHeldEvent event) {
        if(isZoomed(player)) stopZooming(player);
    }
    protected void notZoomed(Player player){
        player.playSound(player, Sound.BLOCK_COMPARATOR_CLICK, 1.0F, 0.3F);
        player.sendActionBar(ChatColor.GOLD + "You have to be zoomed to shoot!");
    }
    public void rangedEntityHit(Player player, EntityDamageByEntityEvent event){
        event.setDamage(damage);
    }

    public void rangedHit(Player player, ProjectileHitEvent event){
        event.getEntity().remove();
    }
}