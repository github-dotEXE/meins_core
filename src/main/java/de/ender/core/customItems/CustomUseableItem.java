package de.ender.core.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomUseableItem extends CustomItem {

    public CustomUseableItem(String name, JavaPlugin plugin) {
        super(name, plugin);
    }

    protected abstract void use(Player player, UseType use);
    protected void switchToSlot(Player player, PlayerItemHeldEvent event){

    }
    protected void switchFromSlot(Player player, PlayerItemHeldEvent event){

    }
}
