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
    private final boolean rotate;

    public CustomFloatText(String text, NamespacedKey id, Display.Billboard billboard, boolean shadowed, boolean rotate) {
        this.text = text;
        this.id = id;
        this.billboard = billboard;
        this.shadowed = shadowed;
        this.rotate = rotate;
    }
    public FloatText spawn(Location location){
        if(isSpawned()) setLocation(location);
        else floatText = FloatTextManager.addFloatText(location,text,shadowed,billboard.name(),rotate,id);
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
