package de.ender.core;

import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

public class MCore {

    public MCore(){

    }

    //log-
    public void log(String msg) {
        getServer().getConsoleSender().sendMessage(msg);
    }
    public void log() {
        log(ChatColor.AQUA + "Presented by:\n███╗░░░███╗███████╗██╗███╗░░██╗░██████╗™\n████╗░████║██╔════╝██║████╗░██║██╔════╝\n██╔████╔██║█████╗░░██║██╔██╗██║╚█████╗░\n██║╚██╔╝██║██╔══╝░░██║██║╚████║░╚═══██╗\n██║░╚═╝░██║███████╗██║██║░╚███║██████╔╝\n╚═╝░░░░░╚═╝╚══════╝╚═╝╚═╝░░╚══╝╚═════╝░");
    }
    //-log

}
