package de.ender.core.modifiers;

import de.ender.core.itemtypes.ItemType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;

public interface Modifier extends Listener {

    /**
     * @return the Item-types the modifier can be applied to
     */
    default EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.ALL);
    }

    /**
     * @return if item fulfills all requirements for modifier (except if ItemTypes match)
     */
    default boolean canApply(ItemStack item){
        return true;
    };

    /**
     * @return the name(key) of the modifier
     */
    String getName();

    /**
     * is called when the modifier got applied to an item
     */
    default void onApply(ItemStack item) {

    }

    /**
     * is called when the modifier got removed from an item
     */
    default void onRemove(ItemStack item) {

    }
}
