package de.ender.core.floattext;

import de.ender.core.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FloatTextCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("core.command.floattext")) return false;
        Player player = ((Player) sender);
        label:
        switch (args[0]) {
            case "add":
                FloatTextManager.addFloatText(player.getLocation(),
                        String.join(" ", Arrays.copyOfRange(args, 4, args.length)),
                        Boolean.parseBoolean(args[2]),
                        args[3],
                        new NamespacedKey(Main.getPlugin(), args[1]));
                break;
            case "remove":
                FloatTextManager.removeFloatText(FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[1])));
                break;
            case "set":
                switch (args[1]) {
                    case "text":
                        FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[2]))
                                .setText(String.join(" ", Arrays.copyOfRange(args, 3, args.length)));
                        break;
                    case "billboard":
                        FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[2]))
                                .setBillboard(args[3]);
                        break;
                    case "shadowed":
                        FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[2]))
                                .setShadowed(Boolean.parseBoolean(args[3]));
                        break;
                }
                break;
            case "put":
                CustomFloatTextManager.getCustomFloatTextByID(NamespacedKey.fromString(args[1]));
                break;
        }
        return true;
    }
}
