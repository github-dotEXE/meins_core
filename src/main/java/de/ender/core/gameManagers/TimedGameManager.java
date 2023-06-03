package de.ender.core.gameManagers;

import de.ender.core.TimeManager;

public class TimedGameManager extends GameManager {
    private final TimeManager timeManager;
    public TimedGameManager(){
        this.timeManager = new TimeManager();
    }
    @Override
    public void start() {
        timeManager.start();
    }
    @Override
    public boolean getStarted() {
        return timeManager.started();
    }
    @Override
    public void end() {
        timeManager.end();
    }
    public TimeManager getTimeManager(){
        return timeManager;
    }
}
