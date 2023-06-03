package de.ender.core.guiManagers;

import de.ender.core.ItemBuilder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class TextGUI extends CustomGUI {
    private final Consumer<String> consumer;
    private final String ifFail;

    @Override
    public void triggerInv(ItemStack item, InventoryClickEvent event) {
        if(!event.getView().getType().equals(InventoryType.ANVIL)) return;
        ItemMeta im = item.getItemMeta();
        if(im == null) return;
        if(item.equals(event.getView().getItem(2))) {
            consumer.accept(PlainTextComponentSerializer.plainText().serialize(im.displayName()));
            GuiListener.unregisterGUI(this);
            event.getInventory().setContents(new ItemStack[0]);
            getPlayer().closeInventory();
        }
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }

    @Override
    public void cancel(InventoryCloseEvent event) {
        consumer.accept(ifFail);
        GuiListener.unregisterGUI(this);
        event.getInventory().setContents(new ItemStack[0]);
    }

    public TextGUI(Player player, String question, String ifFail, Consumer<String> consumer) {
        super(player, "TextGUI");
        this.consumer = consumer;
        this.ifFail = ifFail;

        player.openAnvil(player.getLocation(),true);
        player.getOpenInventory().setTitle(question);
        player.getOpenInventory().setItem(0,new ItemBuilder(Material.PAPER,1).setName(ifFail).build());


    }
}
