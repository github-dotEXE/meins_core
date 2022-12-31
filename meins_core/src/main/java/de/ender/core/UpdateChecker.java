package de.ender.core;

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

        logger.log(ChatColor.GRAY + "Checking for Plugin updates for "+repo_name+"...");
        InputStream is = null;
        try {
            is = new URL("https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/master/version.txt").openStream();
        } catch (MalformedURLException e) {
            logger.log(ChatColor.RED + "Unable to check for updates for repo " + repo_name);
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(ChatColor.RED + "Unable to check for updates for repo " + repo_name);
            e.printStackTrace();
        }

        if (is != null) {
            Scanner scanner = new Scanner(is);
            latest = scanner.next();
            scanner.close();

            upToDate = version.equals(latest);
            if (upToDate) {
                logger.log(ChatColor.GREEN + "Plugin "+ repo_name +" is up to date! "+ChatColor.GRAY+"Version: "+ latest);
            } else {
                logger.log(ChatColor.GOLD + repo_name +" is out of date! Please update. You are still on Version "+ version +", newest is "+latest+"!");
            }
        }
    }

    public boolean upToDate() {
        return upToDate;
    }

    public String getLatestVersion() {
        return latest;
    }
}
