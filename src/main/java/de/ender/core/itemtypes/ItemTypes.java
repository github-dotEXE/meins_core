package de.ender.core.itemtypes;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ItemTypes {
    private static final Map<String,ItemType> itemTypesByKeys = new HashMap<String, ItemType>() {{
        put("_pickaxe",ItemType.PICKAXE);
        put("_axe",ItemType.AXE);
        put("_hoe",ItemType.HOE);
        put("_sword",ItemType.SWORD);
        put("_shovel",ItemType.SHOVEL);
        put("_chestplate",ItemType.CHESTPLATE);
        put("_helmet",ItemType.HELMET);
        put("_leggings",ItemType.LEGGINGS);
        put("_boots",ItemType.BOOTS);
        put("flint_and_steel",ItemType.FNS);
        put("shears",ItemType.SHEARS);
        put("bow",ItemType.BOW);
    }};
    private static final Map<ItemType,EnumSet<ItemType>> cToEItemTypes = new HashMap<ItemType, EnumSet<ItemType>>() {{
        put(ItemType.ALL, EnumSet.allOf(ItemType.class));
        put(ItemType.ALL_ARMOR, EnumSet.of(ItemType.BOOTS,ItemType.HELMET,ItemType.CHESTPLATE, ItemType.LEGGINGS));
        put(ItemType.ALL_TOOLS, EnumSet.of(ItemType.PICKAXE,ItemType.AXE,ItemType.SHOVEL, ItemType.HOE, ItemType.SHEARS));
        put(ItemType.ALL_WEAPONS, EnumSet.of(ItemType.BOW,ItemType.SWORD,ItemType.AXE));
    }};
    public static ItemType getItemType(ItemStack item){
        if(item==null) return ItemType.NONE;
        String name = item.getType().name().toLowerCase();
        for (String s:
                itemTypesByKeys.keySet()) {
            if(name.contains(s)) return itemTypesByKeys.get(s);
        }
        return ItemType.OTHER;
    }
    public static EnumSet<ItemType> getExact(EnumSet<ItemType> cItemTypes){
        ArrayList<ItemType> eItemTypes = new ArrayList<>();
        for (ItemType itemtype:
             cItemTypes) {
            if(cToEItemTypes.containsKey(itemtype)) eItemTypes.addAll(cToEItemTypes.get(itemtype));
            else eItemTypes.add(itemtype);
        }
        return EnumSet.copyOf(eItemTypes);
    }
}
