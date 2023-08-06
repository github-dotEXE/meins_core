package de.ender.core.floattext;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.TextDisplay;

import java.util.UUID;

public class FloatText {
    private final UUID uuid;
    private final NamespacedKey id;
    private TextDisplay entity = null;
    public FloatText(Location location, String text, String alignment,boolean shadowed,NamespacedKey id){
        entity = location.getWorld().spawn(location, TextDisplay.class);
        uuid=entity.getUniqueId();
        entity.setText(text);
        entity.setAlignment(TextDisplay.TextAlignment.valueOf(alignment));
        entity.setShadowed(shadowed);
        this.id = id;
    }
    public FloatText(Location location, String text,NamespacedKey id){
        entity = location.getWorld().spawn(location, TextDisplay.class);
        uuid=entity.getUniqueId();
        entity.setText(text);
        entity.setAlignment(TextDisplay.TextAlignment.CENTER);
        entity.setShadowed(true);
        this.id = id;
    }
    public FloatText(String uuid,NamespacedKey id){
        this.uuid = UUID.fromString(uuid);
        Bukkit.getWorlds().forEach((world)->{
            if(world.getEntity(this.uuid)!=null) entity = (TextDisplay) world.getEntity(this.uuid);
        });
        if(entity==null) FloatTextManager.remove(this);
        this.id = id;
    }
    public void removeEntity(){
        entity.remove();
    }
    public String getText(){
        return entity.getText();
    }
    public void setText(String text){
        entity.setText(text);
    }
    public UUID getUuid(){
        return uuid;
    }
    public NamespacedKey getId(){
        return id;
    }
}
