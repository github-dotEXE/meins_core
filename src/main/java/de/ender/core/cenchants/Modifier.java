package de.ender.core.cenchants;

import org.bukkit.entity.Player;

public interface Modifier {
    default ItemType[] forTypes() {
        return new ItemType[]{ItemType.ALL};
    }

    void onUse(Player player);
    void onStopUse(Player player);
    void onMainHand(Player player);
    void onStopMainHand(Player player);
    void onOffHand(Player player);
    void onStopOffHand(Player player);
}
