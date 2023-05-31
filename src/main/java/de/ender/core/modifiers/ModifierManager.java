package de.ender.core.modifiers;

import de.ender.core.Main;
import de.ender.core.itemtypes.ItemTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ModifierManager implements Listener {
    private static final HashMap<String,Modifier> registeredModifiers = new HashMap<>();
    public static void registerModifier(Modifier modifier){
        Main plugin = Main.getPlugin();
        registeredModifiers.put(modifier.getName(),modifier);
        plugin.getServer().getPluginManager().registerEvents(modifier,plugin);
    }
    public static Modifier getModifierByName(String name){
        return registeredModifiers.get(name);
    }
    public static Collection<Modifier> getAll() {
        return registeredModifiers.values();
    }
    public static Collection<Modifier> getItemModifiers(ItemStack item){
        Collection<Modifier> modifiers = new ArrayList<>();
        List<Component> lore = item.lore();
        if(lore == null) lore = new ArrayList<>();
        for (Component c:
             lore) {
            String sc =  PlainTextComponentSerializer.plainText().serialize(c);
            if(registeredModifiers.containsKey(sc)){
                modifiers.add(registeredModifiers.get(sc));
            }
        }
        return modifiers;
    }
    public static boolean hasItemModifier(ItemStack item, Modifier modifier){
        return getItemModifiers(item).contains(modifier);
    }
    public static boolean applyModifier(ItemStack item,Modifier modifier){
        if(modifier != null && canApply(modifier,item) && modifier.canApply(item) && !hasItemModifier(item,modifier)) {
            List<Component> lore = item.lore();
            if(lore == null) lore = new ArrayList<>();
            lore.add(Component.text(modifier.getName()));
            item.lore(lore);
            modifier.onApply(item);
            return true;
        }
        return false;
    }
    public static boolean removeModifier(ItemStack item, Modifier modifier){
        List<Component> lore = item.lore();
        if(lore == null || modifier == null || !hasItemModifier(item,modifier)) return false;
        List<Component> lore2 = new ArrayList<>();
        for (Component c: lore) {
            String sc =  PlainTextComponentSerializer.plainText().serialize(c);
            if(registeredModifiers.containsKey(sc) && registeredModifiers.get(sc) == modifier){
                lore2.add(c);
                lore.removeAll(lore2);
                item.lore(lore);
                modifier.onRemove(item);
                return true;
            }
        }
        return false;
    }
    public static boolean canApply(Modifier modifier,ItemStack item){
        return ItemTypes.getExact(modifier.forTypes()).contains(ItemTypes.getItemType(item));
    }
}
