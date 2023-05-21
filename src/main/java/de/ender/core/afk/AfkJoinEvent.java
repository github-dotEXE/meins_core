package de.ender.core.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AfkJoinEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public AfkJoinEvent(Player player){
        super();
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public @NotNull Player getPlayer() {
        return this.player;
    }

}
