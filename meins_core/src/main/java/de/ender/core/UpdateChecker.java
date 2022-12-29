package de.ender.core;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    MCore logger = new MCore();

    private static boolean upToDate = false;
    private static String latest = "";

    public void check(String version,String github_profile_name, String repo_name) {

        logger.log(ChatColor.GRAY + "Checking for mod updates...");
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/master/version.txt").openStream();
        } catch (MalformedURLException e) {
            logger.log(ChatColor.RED + "Unable to check for updates!");
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(ChatColor.RED + "Unable to check for updates!");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(in);
        latest = scanner.next();
        //Bukkit.getServer().sendMessage(Component.text(latest));

        logger.log(ChatColor.GRAY + latest + ".");
        upToDate = version.equals(latest);
        if (upToDate) {
            //Bukkit.getServer().sendMessage(Component.text("latest"));
            logger.log(ChatColor.GREEN + "Plugin "+ repo_name +" is the latest version!");
        } else {
            //Bukkit.getServer().sendMessage(Component.text("not latest"));
            logger.log(ChatColor.GOLD + repo_name +" is out of date! Please update. You are still on Version "+ version +"!");
        }
    }

    public boolean upToDate() {
        return upToDate;
    }

    public String getLatestVersion() {
        return latest;
    }
}
