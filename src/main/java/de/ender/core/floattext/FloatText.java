package de.ender.core.floattext;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;

import java.util.UUID;

public class FloatText {
    private final UUID uuid;
    private final NamespacedKey id;
    private TextDisplay entity;
    public FloatText(Location location, String text, boolean shadowed, Display.Billboard billboard,float yaw,float pitch,boolean seethrough, NamespacedKey id){
        entity = location.getWorld().spawn(location, TextDisplay.class);
        uuid=entity.getUniqueId();
        entity.text(MiniMessage.miniMessage().deserialize(text));
        entity.setAlignment(TextDisplay.TextAlignment.CENTER);
        entity.setShadowed(shadowed);
        entity.setBillboard(billboard);
        entity.setRotation(yaw,pitch);
        entity.setSeeThrough(seethrough);
        this.id = id;
    }
    public FloatText(String uuid,NamespacedKey id){
        this.uuid = UUID.fromString(uuid);
        entity = (TextDisplay) Bukkit.getServer().getEntity(UUID.fromString(uuid));
        this.id = id;
    }
    private void putEntityIfNull(){
        if(entity==null) entity = (TextDisplay) Bukkit.getServer().getEntity(uuid);
    }
    public void removeEntity(){
        putEntityIfNull();
        entity.remove();
    }
    public void setShadowed(boolean shadowed){
        putEntityIfNull();
        entity.setShadowed(shadowed);
    }
    public TextDisplay getEntity(){
        putEntityIfNull();
        return entity;
    }
    public void setBillboard(String billboard){
        putEntityIfNull();
        entity.setBillboard(Display.Billboard.valueOf(billboard));
    }
    public String getText(){
        putEntityIfNull();
        return entity.getText();
    }
    public void setText(String text){
        putEntityIfNull();
        entity.text(MiniMessage.miniMessage().deserialize(text));
    }
    public UUID getUuid(){
        return uuid;
    }
    public NamespacedKey getId(){
        return id;
    }
}
