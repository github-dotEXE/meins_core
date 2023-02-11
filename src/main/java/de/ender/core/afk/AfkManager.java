package de.ender.core.afk;

import de.ender.core.Log;
import de.ender.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class AfkManager implements Listener {
    public static final long TIME_FOR_AFK =60*1000;
    private static final HashMap<Player,Long> lastMovement = new HashMap<>();
    private static final HashMap<Player,BukkitTask> currentTask = new HashMap<>();

    public static void playerJoin(Player player){
        lastMovement.put(player,System.currentTimeMillis());
        currentTask.put(player,timeAFK(player,lastMovement.get(player),player.getLocation()));
    }

    public static void playerLeave(Player player){
        lastMovement.remove(player);
    }

    public static void playerMove(Player player){
        boolean wasAFK = isAFK(player);
        lastMovement.put(player,System.currentTimeMillis());
        boolean nowAFK = isAFK(player);

        if(wasAFK && !nowAFK) {
            callStop(player);
        } else if(!wasAFK && nowAFK){
            callStart(player);
        }
    }

    private static BukkitTask timeAFK(Player player, long oldLastMovement, Location oldLoc){
        return new BukkitRunnable() {
            @Override
            public void run() {
                if(oldLastMovement == lastMovement.get(player)){
                    callStart(player);
                }
                currentTask.put(player,null);
            }
        }.runTaskLater(Main.getPlugin(),(TIME_FOR_AFK/1000)*20);
    }

    public static boolean isAFK(Player player){
        return getAFKTime(player) >= TIME_FOR_AFK;
    }

    public static long getAFKTime(Player player){
        return System.currentTimeMillis()-lastMovement.get(player);
    }

    public static void setAFK(Player player,boolean nowAfk){
        if(nowAfk && !isAFK(player)){
            lastMovement.put(player,System.currentTimeMillis()-TIME_FOR_AFK);
            callStart(player);
        } else if(!nowAfk && isAFK(player)){
            lastMovement.put(player,System.currentTimeMillis());
            callStop(player);
        }
    }

    private static void callStart(Player player){
        AfkStartEvent event = new AfkStartEvent(player);
        Bukkit.getPluginManager().callEvent(event);
    }
    private static void callStop(Player player){
        AfkStopEvent event = new AfkStopEvent(player);
        Bukkit.getPluginManager().callEvent(event);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        playerMove(player);
        BukkitTask task = currentTask.get(player);
        if(task != null){
            task.cancel();
            currentTask.put(player,null);
        }
        currentTask.put(player,timeAFK(player,lastMovement.get(player),player.getLocation()));
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerJoin(event.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        playerLeave(event.getPlayer());
    }
}
