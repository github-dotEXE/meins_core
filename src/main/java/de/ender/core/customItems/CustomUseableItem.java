package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;

public abstract class CustomUseableItem extends CustomItem {

    protected abstract void use(Player player, UseType use);
    protected void switchToSlot(Player player, PlayerItemHeldEvent event){

    }
    protected void switchFromSlot(Player player, PlayerItemHeldEvent event){

    }
}
