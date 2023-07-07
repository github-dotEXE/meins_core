package de.ender.core.customItems;

import de.ender.core.ItemBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomItemsCMD implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(!player.hasPermission("core.command.custom_items")||args.length<1) return false;
        ItemStack item = CustomItem.getCustomItem(args[0]).getItem();
        if(args.length == 2) item =new ItemBuilder(item).setAmount(Integer.parseInt(args[1])).build();
        player.getInventory().addItem(item);
        return true;
    }
}
