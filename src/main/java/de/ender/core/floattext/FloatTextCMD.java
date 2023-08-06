package de.ender.core.floattext;

import de.ender.core.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;
import java.util.Arrays;

public class FloatTextCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)||!sender.hasPermission("core.command.floattext")) return false;
        Player player = ((Player) sender);
        switch (args[0]){
            case "add":
                FloatTextManager.addFloatText(player.getLocation(),
                        String.join(" ", Arrays.copyOfRange(args, 1, args.length)),
                        Boolean.parseBoolean(args[2]),
                        new NamespacedKey(Main.getPlugin(),args[1]));
                break;
            case "remove":
                FloatTextManager.removeFloatText(FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(),args[1])));
                break;
            case "set":
                if(args[1].equals("text")){

                }else if(args[1].equals("turns")){

                }
                FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(),args[1])).setText(
                        String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
                break;
        }
        return true;
    }
}
