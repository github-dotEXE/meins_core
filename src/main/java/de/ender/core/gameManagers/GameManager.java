package de.ender.core.gameManagers;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager{
    private final List<Player> players;
    private boolean started;
    public GameManager(){
        players = new ArrayList<>();
        started = false;
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public List<Player> getPlayers(){
        return players;
    }
    public void addPlayers(List<Player> players){
        this.players.addAll(players);
    }
    public void start(){
        this.started = true;
    }
    public boolean getStarted(){
        return started;
    }
    public void end(){
        this.started = false;
    }
}
