package de.ender.core.afk;

import de.ender.core.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AfkStopEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public AfkStopEvent(Player player){
        super();
        this.isCancelled = false;
        this.player = player;
        Log.info(this.player.getName()+" is no longer AFK!");
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public @NotNull Player getPlayer() {
        return player;
    }
}
