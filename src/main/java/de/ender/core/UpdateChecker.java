package de.ender.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class UpdateChecker {

    private boolean upToDate;
    private String latest;
    private final String version;
    private final String githubProfileName;
    private final String repoName;
    private final String branchName;

    public UpdateChecker(JavaPlugin plugin,String github_profile_name, String repo_name, String branch_name){
        this.upToDate = false;
        this.githubProfileName = github_profile_name;
        this.repoName = repo_name;
        this.branchName = branch_name;

        this.version = plugin.getPluginMeta().getVersion();
        getLatest();
    }
    public UpdateChecker(JavaPlugin meins_plugin,String branchName){
        this.githubProfileName = "github-dotEXE";
        this.branchName = branchName;
        this.upToDate = false;
        this.repoName = meins_plugin.getPluginMeta().getName();

        this.version = meins_plugin.getPluginMeta().getVersion();
        getLatest();
    }

    /**
     * format codes:
     *      '${version}'
     *      '${name}'
     */
    public UpdateChecker downloadLatest(final String formattableURL,final String name){
        new BukkitRunnable() {
            @Override
            public void run() {
                String formattedURL = formattableURL.replace("${version}",latest).replace("${name}",name);
                String fileName = formattedURL.split("/")[formattedURL.split("/").length-1];

                File oldfile = new File(Main.getPlugin().getDataFolder().getParentFile(),fileName.replace(latest,version));
                File newfile = new File(Main.getPlugin().getDataFolder().getParentFile(),fileName);
                try {
                    if (Float.parseFloat(version) > Float.parseFloat(latest)) {
                        Log.warn("Repo "+repoName+" isn't up to date!");
                        return;
                    } else if(Float.parseFloat(version) == Float.parseFloat(latest)) return;
                } catch (NumberFormatException e) {
                    Log.warn("Couldn't insure that repo isn't up to date!");
                }

                try {
                    URL website = new URL(formattedURL);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(newfile);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    Log.success("Successfully downloaded latest file from: <gray>"+ formattedURL);
                    try{
                        Files.delete(oldfile.toPath());
                    }catch (NoSuchFileException exception){
                        Log.error("Unable to delete old jar of: <gray>"+ name);
                    }
                } catch (IOException e) {
                    Log.error("Unable to download latest file from: <gray>"+ formattedURL);
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
        return this;
    }
    public UpdateChecker downloadLatestMeins(String name){
        return downloadLatest("http://repo.etwas--anders.de/releases/de/ender/${name}/${version}/${name}-${version}.jar",name);
    }
    public UpdateChecker downloadLatestMeins(){
        String name;
        if(repoName.contains("meins_")) name = repoName.substring(6);
        else name = repoName;
        return downloadLatestMeins(name);
    }

    private void getLatest() {
        Log.info("Checking for Plugin updates for: <gray>"+repoName);
        String url ="https://raw.githubusercontent.com/"+githubProfileName+"/"+repoName+"/"+branchName+"/pom.xml";
        try {
            InputStream pomStream = new URL(url).openStream();
            Scanner scanner = new Scanner(pomStream);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("<version>")) {
                    latest = line.substring(line.indexOf("<version>")+9,line.indexOf("</version>"));
                    break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            Log.warn("No pom.xml found in: <gray>"+url);
            Log.error("Unable to check for updates for repo: <gray>" + repoName);
        }
    }

    public boolean isUpToDate() {
        return upToDate;
    }

    public String getLatestVersion() {
        return latest;
    }

    public UpdateChecker check() {
        upToDate = version.equals(latest);
        if(!upToDate) {
            upToDate = version.equals(latest + "0");
            if(upToDate) latest += "0";
        }
        if (upToDate) Log.info("Plugin " + repoName + " is up to date! <gray>" + "Version: " + latest);
        else Log.warn(repoName + " is out of date! Please update. You are still on Version " + version + ", newest is " + latest + "!");
        return this;
    }
}
