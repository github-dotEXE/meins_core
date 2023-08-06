package de.ender.core.floattext;

import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomFloatTextManager {
    private static final HashMap<NamespacedKey,CustomFloatText> customfloattexts = new HashMap<>();
    public static ArrayList<String> getCustomFloatTexts(){
        ArrayList<String> ids = new ArrayList<>();
        customfloattexts.keySet().forEach((namespacedKey -> ids.add(namespacedKey.asString())));
        return ids;
    }
    public static void addCustomFloatText(CustomFloatText floatText){
        customfloattexts.put(floatText.getId(),floatText);
    }
    public static CustomFloatText getCustomFloatTextByID(NamespacedKey id){
        return customfloattexts.get(id);
    }
}
