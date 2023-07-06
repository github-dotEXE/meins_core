package de.ender.core.weapons;

import de.ender.core.customItems.CustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WeaponsCMD implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(!player.hasPermission("weapons.command.weapons")||args.length!=1) return false;
        player.getInventory().addItem( CustomItems.getCustomItem(args[0]).getItem() );
        return true;
    }
}
