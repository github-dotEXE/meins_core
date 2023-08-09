package de.ender.core.floattext;

import de.ender.core.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
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
                        String.join(" ", Arrays.copyOfRange(args, 6, args.length))
                                .replace("&", "§").replace("//", "\n"),
                        Boolean.parseBoolean(args[2]),
                        Display.Billboard.valueOf(args[3]),
                        player.getLocation().getYaw(),
                        Float.parseFloat(args[4]),
                        Boolean.parseBoolean(args[5]),
                        new NamespacedKey(Main.getPlugin(),args[1]));
                break;
            case "remove":
                FloatTextManager.removeFloatText(FloatTextManager.getByID(NamespacedKey.fromString(args[1])));
                break;
            case "set":
                FloatText floatText = FloatTextManager.getByID(NamespacedKey.fromString(args[2]));
                switch (args[1]) {
                    case "text":
                        floatText.setText(String.join(" ", Arrays.copyOfRange(args, 3, args.length))
                                        .replace("&", "§").replace("//", "\n"));
                        break;
                    case "billboard":
                        floatText.setBillboard(args[3]);
                        break;
                    case "shadowed":
                        floatText.setShadowed(Boolean.parseBoolean(args[3]));
                        break;
                    case "rotation":
                        if(args.length==3) floatText.getEntity().setRotation(player.getLocation().getYaw(),player.getLocation().getPitch());
                        else floatText.getEntity().setRotation(Float.parseFloat(args[3]),Float.parseFloat(args[4]));
                        break;
                    case "seethrough":
                        floatText.getEntity().setSeeThrough(Boolean.parseBoolean(args[3]));
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
