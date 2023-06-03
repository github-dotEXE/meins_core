package de.ender.core;

public class TimeManager {
    private long startTime;
    private long endTime;
    public TimeManager(){
        this.startTime = -1;
        this.endTime = -1;
    }
    public long getStartTime(){
        return this.startTime;
    }
    public void end(){
        this.endTime = System.currentTimeMillis();
    }
    public boolean ended(){
        return this.endTime != -1;
    }
    public long getEndTime(){
        return this.endTime;
    }
    public void start(){
        this.startTime = System.currentTimeMillis();
    }
    public boolean started(){
        return this.startTime != -1;
    }
    public long timeSinceStart(){
        return System.currentTimeMillis()-this.startTime;
    }
    public long totalTime(){
        return this.endTime-this.startTime;
    }
}
