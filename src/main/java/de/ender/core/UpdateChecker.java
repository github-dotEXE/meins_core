package de.ender.core;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * checks for updates and sends info in console
 */
public class UpdateChecker {

    private static boolean upToDate = false;
    private static String latest = "";

    /**
     * gets repo name from plugin.yml name
     *
     * @param github_profile_name the name of the GitHub profile
     * @param  plugin  the Main class (this if you are in Main)
     * @param  branch_name the GitHub branch in which to look for the files
     * @param  version the version of this plugin
     * @see UpdateChecker
     */
    public static void check(Plugin plugin,String branch_name, String version,String github_profile_name) {
        PluginDescriptionFile description = plugin.getDescription();

        String repo_name = description.getName();

        check(version,github_profile_name,repo_name,branch_name);
    }

    /**
     * gets repo name from plugin.yml name
     * gets GitHub profile name from first author description
     *
     * @param  plugin  the Main class (this if you are in Main)
     * @param  branch_name the GitHub branch in which to look for the files
     * @param  version the version of this plugin
     * @see UpdateChecker
     */
    public static void check(Plugin plugin,String branch_name, String version) {
        PluginDescriptionFile description = plugin.getDescription();

        String repo_name = description.getName();
        String github_profile_name = description.getAuthors().get(0);

        check(version,github_profile_name,repo_name,branch_name);
    }
    /**
     * gets repo name from plugin.yml name
     * gets GitHub profile name from plugin.yml authors (the first)
     * gets version from plugin.yml
     *
     * @param  plugin  the Main class (this if you are in Main)
     * @param  branch_name the GitHub branch in which to look for the files
     * @see UpdateChecker
     */
    public static void check(Plugin plugin,String branch_name) {
        PluginDescriptionFile description = plugin.getDescription();

        String repo_name = description.getName();
        String version = description.getVersion();
        String github_profile_name = description.getAuthors().get(0);
        check(version,github_profile_name,repo_name,branch_name);
    }

    /**
     * always uses "master" branch
     *
     * @param  version the version of this plugin
     * @param github_profile_name the profile name on GitHub
     * @param repo_name the repository name on GitHub
     * @see UpdateChecker
     */
    @Deprecated
    public static void check(String version, String github_profile_name, String repo_name) {
        check(version,github_profile_name,repo_name,"master");
    }

    /**
     *
     * @param branch_name the name of the branch on GitHub
     * @param  version the version of this plugin
     * @param github_profile_name the profile name on GitHub
     * @param repo_name the repository name on GitHub
     * @see UpdateChecker
     */
    public static void check(String version, String github_profile_name, String repo_name, String branch_name) {
        Log.log(ChatColor.GRAY + "Checking for Plugin updates for "+repo_name+"...");
        InputStream vStream = null;
        InputStream pomStream = null;
        try {
            pomStream = new URL("https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/"+branch_name+"/pom.xml").openStream();
        } catch (IOException e) {
            Log.log(ChatColor.YELLOW + "No pom.xml found in "+ "https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/"+branch_name+"/");
        }
        try {
            vStream = new URL("https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/"+branch_name+"/version.txt").openStream();
        } catch (IOException e) {
            Log.log(ChatColor.YELLOW + "No version.txt found in "+ "https://raw.githubusercontent.com/"+github_profile_name+"/"+repo_name+"/"+branch_name+"/");
        }

        if(pomStream!=null){
            Scanner scanner = new Scanner(pomStream);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("<version>")) {
                    latest = line.substring(line.indexOf("<version>")+9,line.indexOf("</version>"));
                    break;
                }
            }
            scanner.close();
        } else if (vStream != null ) {
            Scanner scanner = new Scanner(vStream);
            latest = scanner.next();
            scanner.close();
        } else Log.log(ChatColor.RED + "Unable to check for updates for repo " + repo_name);
        checkUTD(latest,version,repo_name);
    }

    public static boolean upToDate() {
        return upToDate;
    }

    public static String getLatestVersion() {
        return latest;
    }

    private static void checkUTD(String latest,String version, String repo_name) {
        upToDate = version.equals(latest);
        if (upToDate) {
            Log.log(ChatColor.GREEN + "Plugin "+ repo_name +" is up to date! "+ChatColor.GRAY+"Version: "+ latest);
        } else {
            Log.log(ChatColor.GOLD + repo_name +" is out of date! Please update. You are still on Version "+ version +", newest is "+latest+"!");
        }
    }
}
