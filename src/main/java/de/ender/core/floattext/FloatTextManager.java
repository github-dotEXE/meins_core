package de.ender.core.floattext;

import de.ender.core.CConfig;
import de.ender.core.Main;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FloatTextManager {
    private static final HashMap<UUID,FloatText> floatTexts = new HashMap<>();
    private static final HashMap<NamespacedKey,FloatText> floatTextIDs = new HashMap<>();
    static void remove(FloatText floatText) {
        floatTexts.remove(floatText.getUuid());
        floatTextIDs.remove(floatText.getId());
        CConfig cConfig = new CConfig("floattext", Main.getPlugin());
        FileConfiguration config = cConfig.getCustomConfig();

        config.set(floatText.getUuid().toString(),null);

        cConfig.save();
    }
    public static void removeFloatText(FloatText floatText){
        remove(floatText);
        floatText.removeEntity();
    }
    public static void init(){
        CConfig cConfig = new CConfig("floattext", Main.getPlugin());
        FileConfiguration config = cConfig.getCustomConfig();

        config.getValues(false).forEach((uuidS,namespacedkeyS)->{
            UUID uuid = UUID.fromString(uuidS);
            NamespacedKey id = NamespacedKey.fromString((String) namespacedkeyS);
            FloatText floatText = new FloatText(uuidS,id);
            floatTexts.put(uuid,floatText);
            floatTextIDs.put(id,floatText);
        });

    }
    public static ArrayList<String> getIDList(){
        ArrayList<String> ids = new ArrayList<>();
        floatTextIDs.keySet().forEach((id)-> ids.add(id.asString()));
        return ids;
    }
    public static FloatText getByUUID(UUID uuid){
        return floatTexts.get(uuid);
    }
    public static FloatText getByID(NamespacedKey id){
        return floatTextIDs.get(id);
    }
    public static FloatText addFloatText(Location location, String text, boolean shadowed, Display.Billboard billboard, float yaw, float pitch,boolean seethrough, NamespacedKey id){
        if(getByID(id)!=null) removeFloatText(getByID(id));
        FloatText floatText = new FloatText(location,text,shadowed,billboard,yaw,pitch,seethrough,id);
        UUID uuid = floatText.getUuid();
        floatTexts.put(uuid,floatText);
        floatTextIDs.put(id,floatText);
        CConfig cConfig = new CConfig("floattext", Main.getPlugin());
        FileConfiguration config = cConfig.getCustomConfig();

        config.set(uuid.toString(),id.asString());

        cConfig.save();
        return floatText;
    }
}
