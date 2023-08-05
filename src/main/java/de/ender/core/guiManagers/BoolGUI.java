package de.ender.core.guiManagers;

import de.ender.core.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class BoolGUI extends CustomGUI {
    private static final ItemStack trueItem = new ItemBuilder(Material.GREEN_TERRACOTTA,1).setName(ChatColor.GREEN+"True").build();
    private static final ItemStack falseItem = new ItemBuilder(Material.RED_TERRACOTTA,1).setName(ChatColor.RED+"False").build();
    private static final ItemStack fillItem = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE,1).setName("-").build();
    private final Consumer<Boolean> consumer;
    private final Boolean ifFail;
    @Override
    public void triggerInv(ItemStack item, InventoryClickEvent event) {
        if(item.equals(trueItem)){
            event.getView().close();
            consumer.accept(true);
            unregisterGUI(this);
        } else if(item.equals(falseItem)){
            event.getView().close();
            consumer.accept(false);
            unregisterGUI(this);
        }
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }
    @Override
    public void cancel(InventoryCloseEvent event) {
        consumer.accept(ifFail);
        unregisterGUI(this);
    }

    public BoolGUI(Player player, String question, Boolean ifFail,Consumer<Boolean> consumer){
        super(player,"BoolGUI");
        this.ifFail = ifFail;
        this.consumer = consumer;

        ItemStack questionItem = new ItemBuilder(Material.ORANGE_TERRACOTTA,1).setName(question).build();
        player.openInventory(new CGUI(9,super.getName()+" - "+question)
                .setGuiSpace(3,0,trueItem)
                .setGuiSpace(5,0,falseItem)
                .setGuiSpace(4,0,questionItem)
                .fillEmpty(fillItem)
                .getGui());
    }
}
