package de.ender.core.floattext;

import de.ender.core.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FloatTextCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("core.command.floattext")) return false;
        Player player = ((Player) sender);

        switch (args[0]) {
            case "add":
                FloatTextManager.addFloatText(player.getLocation(),
                        String.join(" ", Arrays.copyOfRange(args, 5, args.length))
                                .replace("&", "§").replace("//", "\n"),
                        Boolean.parseBoolean(args[2]),
                        Display.Billboard.valueOf(args[3]),
                        Float.parseFloat(args[4]),
                        player.getLocation().getPitch(),
                        new NamespacedKey(Main.getPlugin(), args[1]));
                break;
            case "remove":
                FloatTextManager.removeFloatText(FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[1])));
                break;
            case "set":
                switch (args[1]) {
                    case "text":
                        FloatTextManager.getByID(new NamespacedKey(Main.getPlugin(), args[2]))
                                .setText(String.join(" ", Arrays.copyOfRange(args, 3, args.length))
                                        .replace("&", "§").replace("//", "\n"));
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
                CustomFloatTextManager.getCustomFloatTextByID(NamespacedKey.fromString(args[1])).spawn(player.getLocation());
                break;
        }
        return true;
    }
}
