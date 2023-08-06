package de.ender.core.floattext;

import de.ender.core.CConfig;
import de.ender.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.TextDisplay;

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
        floatText.removeEntity();
        remove(floatText);
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
        floatTextIDs.keySet().forEach((id)-> {
            if(id.getNamespace().equals(Main.getPlugin().getPluginMeta().getName())) ids.add(id.getKey());
        });
        return ids;
    }
    public static FloatText getByUUID(UUID uuid){
        return floatTexts.get(uuid);
    }
    public static FloatText getByID(NamespacedKey id){
        return floatTextIDs.get(id);
    }
    public static void addFloatText(Location location, String text,boolean turns, NamespacedKey id){
        FloatText floatText = new FloatText(location,text,turns,id);
        UUID uuid = floatText.getUuid();
        floatTexts.put(uuid,floatText);
        floatTextIDs.put(id,floatText);
        CConfig cConfig = new CConfig("floattext", Main.getPlugin());
        FileConfiguration config = cConfig.getCustomConfig();

        config.set(uuid.toString(),id.asString());

        cConfig.save();
    }
}
