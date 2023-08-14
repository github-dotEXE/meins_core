package de.ender.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class Log {
    private static final ConsoleCommandSender console = getServer().getConsoleSender();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void info(String message){
        log("<white>[INFO]: <reset>"+message);
    }
    public static void warn(String message){
        log("<yellow>[WARN]: <reset>"+message);
    }
    public static void error(String message){
        log("<red>[ERROR]: <reset>"+message);
    }
    public static void criticalError(String message){
        log("<dark_red>[CRITICAL ERROR]: <reset>"+message);
    }
    public static void success(String message){
        log("<dark_green>[SUCCESS]: <reset>"+message);
    }
    public static void printLogo(){
        info("<aqua>"+
                "Presented by:"+ lf()+
                "███╗░░░███╗███████╗██╗███╗░░██╗░██████╗™"+ lf() +
                "████╗░████║██╔════╝██║████╗░██║██╔════╝"+ lf() +
                "██╔████╔██║█████╗░░██║██╔██╗██║╚█████╗░"+ lf() +
                "██║╚██╔╝██║██╔══╝░░██║██║╚████║░╚═══██╗"+ lf() +
                "██║░╚═╝░██║███████╗██║██║░╚███║██████╔╝"+ lf() +
                "╚═╝░░░░░╚═╝╚══════╝╚═╝╚═╝░░╚══╝╚═════╝░");
    }
    private static String lf(){
        return "\n";
    }

    public static void log(String message){
        console.sendMessage(miniMessage.deserialize(message));
    }
    public static void log(Component message){
        console.sendMessage(message);
    }
    public static void enable(JavaPlugin plugin){
        info("<green>Enabling <reset>"+plugin.getName());
    }
    public static void disable(JavaPlugin plugin){
        info("<red>Disabling <reset>"+plugin.getName());
    }
}
