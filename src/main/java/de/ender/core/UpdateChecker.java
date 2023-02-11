package de.ender.core;

import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private static boolean upToDate = false;
    private static String latest = "";

    public static void check(String version, String github_profile_name, String repo_name) {

        Log.log(ChatColor.GRAY + "Checking for Plugin updates for "+repo_name+"...");
        InputStream is = null;
        try {
            is = new URL("https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/master/version.txt").openStream();
        } catch (MalformedURLException e) {
            Log.log(ChatColor.RED + "Unable to check for updates for repo " + repo_name);
            e.printStackTrace();
        } catch (IOException e) {
            Log.log(ChatColor.RED + "Unable to check for updates for repo " + repo_name);
            e.printStackTrace();
        }

        if (is != null) {
            Scanner scanner = new Scanner(is);
            latest = scanner.next();
            scanner.close();

            upToDate = version.equals(latest);
            if (upToDate) {
                Log.log(ChatColor.GREEN + "Plugin "+ repo_name +" is up to date! "+ChatColor.GRAY+"Version: "+ latest);
            } else {
                Log.log(ChatColor.GOLD + repo_name +" is out of date! Please update. You are still on Version "+ version +", newest is "+latest+"!");
            }
        }
    }

    public static boolean upToDate() {
        return upToDate;
    }

    public static String getLatestVersion() {
        return latest;
    }
}
