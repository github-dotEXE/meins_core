package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class Log {
    public static void info(String message){
        getServer().getConsoleSender().sendMessage("[INFO]: "+ChatColor.WHITE+message);
    }
    public static void warn(String message){
        getServer().getConsoleSender().sendMessage("[WARN]: "+ChatColor.GOLD+message);
    }
    public static void error(String message){
        getServer().getConsoleSender().sendMessage("[ERROR]: "+ChatColor.RED+message);
    }
    public static void criticalError(String message){
        getServer().getConsoleSender().sendMessage("[CRITICAL ERROR]: "+ChatColor.DARK_RED+message);
    }
    public static void success(String message){
        getServer().getConsoleSender().sendRawMessage("[SUCCESS]: "+ChatColor.GREEN+message);
    }
    public static void printLogo(){
        Log.log(ChatColor.AQUA + "Presented by:\n███╗░░░███╗███████╗██╗███╗░░██╗░██████╗™\n████╗░████║██╔════╝██║████╗░██║██╔════╝\n██╔████╔██║█████╗░░██║██╔██╗██║╚█████╗░\n██║╚██╔╝██║██╔══╝░░██║██║╚████║░╚═══██╗\n██║░╚═╝░██║███████╗██║██║░╚███║██████╔╝\n╚═╝░░░░░╚═╝╚══════╝╚═╝╚═╝░░╚══╝╚═════╝░");
    }
    public static void log(String message){
        getServer().getConsoleSender().sendRawMessage(message);
    }
    public static void enable(JavaPlugin plugin){
        info("Enabling "+plugin.getName()+"...");
    }
}
