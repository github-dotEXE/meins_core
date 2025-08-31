package de.ender.core;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class UpdateCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)||!sender.hasPermission("core.command.update")) return false;
        String[] arguments = Arrays.copyOfRange(args,1,args.length);
        if (args[0].equals("force")) {
            return force(player, arguments);
        }
        return false;
    }
    private static boolean force(Player player, String[] args){
        new UpdateChecker(args[0],args[1]).downloadLatestMeins();
        player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>Downloading..."));
        return true;
    }
}
