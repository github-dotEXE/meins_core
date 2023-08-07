package de.ender.core.floattext;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;

public class CustomFloatText{
    private final String text;
    private final NamespacedKey id;
    private final Display.Billboard billboard;
    private final boolean shadowed;
    private FloatText floatText = null;
    private float yaw;
    private float pitch;

    public CustomFloatText(String text, NamespacedKey id, Display.Billboard billboard, boolean shadowed,float yaw,float pitch) {
        this.text = text;
        this.id = id;
        this.billboard = billboard;
        this.shadowed = shadowed;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public FloatText spawn(Location location){
        if(!(-180<yaw&&yaw<180)) yaw = location.getYaw();
        if(!(-90<pitch&&pitch<90)) pitch = location.getPitch();

        if(isSpawned()) setLocation(location);
        else floatText = FloatTextManager.addFloatText(location,text,shadowed,billboard,yaw,pitch,id);
        return floatText;
    }
    public FloatText getFloatText(){
        return floatText;
    }
    public boolean isSpawned(){
        return floatText!=null;
    }

    public NamespacedKey getId() {
        return id;
    }
    public void setLocation(Location location){
        getFloatText().getEntity().teleport(location);
    }
}
