package de.ender.core;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class PluginMessageManager implements PluginMessageListener, Listener {
    private final HashMap<String,Consumer<DataInputStream>> customPluginMessageListenerMap = new HashMap<>();

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);

        String subChannel = input.readUTF();
        switch(subChannel){
            case "ServerIP":
                onServerIp(input);
                break;
            case "IP":
            case "IPOther":
                onIP(input);
                break;
            case "PlayerCount":
                onPlayerCount(input);
                break;
            case "PlayerList":
                onPlayerList(input);
                break;
            case "GetServers":
                onServerList(input);
                break;
            default:
                onCustomMessage(subChannel,input);
                break;
        }
    }

    private void onCustomMessage(String subChannel, ByteArrayDataInput input) {
        if(!customPluginMessageListenerMap.containsKey(subChannel)) return;
        Consumer<DataInputStream> consumer = customPluginMessageListenerMap.get(subChannel);

        short len = input.readShort();
        byte[] msgbytes = new byte[len];
        input.readFully(msgbytes);

        DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
        consumer.accept(msgin);
    }

    private void onServerIp(ByteArrayDataInput input){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        String serverName = input.readUTF();
        String ip = input.readUTF();
        int port = input.readUnsignedShort();

        config.set("serverIP."+serverName+".ip",ip);
        config.set("serverIP."+serverName+".port",port);
        cconfig.save();
    }
    private void onIP(ByteArrayDataInput input){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        String playerName = input.readUTF();
        String address = input.readUTF();
        //int port = input.readInt();

        config.set("playerIP."+playerName,address);
        cconfig.save();
    }
    private void onPlayerCount(ByteArrayDataInput input){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        String server = input.readUTF();
        int playercount = input.readInt();

        config.set("serverPlayerAmount."+server,playercount);
        cconfig.save();
    }
    private void onPlayerList(ByteArrayDataInput input){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        String server = input.readUTF();
        String playerlist = input.readUTF();

        config.set("serverPlayerList."+server,playerlist);
        cconfig.save();
    }
    private void onServerList(ByteArrayDataInput input){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        String serverlist = input.readUTF();

        config.set("serverList",serverlist);
        cconfig.save();
    }

    public static void connect(Player player, String serverName) {
        sendPluginMessage(player,"Connect "+serverName);
    }
    public static void connect(String playerName,String serverName){
        sendPluginMessageAny("ConnectOther "+playerName+" "+serverName);
    }
    public static void connectSafely(String playerName,String serverName){
        isServerOnline(serverName,(a) -> {if(a) connect(playerName, serverName);} );
    }
    public static void ip(Player player,Consumer<String> consumer){
        ip(player.getName(),consumer);
    }
    public static void ip(String playerName, Consumer<String> consumer){
        Main plugin = Main.getPlugin();
        sendPluginMessageAny("IPOther "+playerName);
        new BukkitRunnable() {
            @Override
            public void run() {
                Main plugin = Main.getPlugin();
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();
                String ip = config.getString("playerIP."+playerName);
                config.set("playerIP."+playerName,null);
                cconfig.save();
                consumer.accept(ip);
            }
        }.runTaskLaterAsynchronously(plugin, 10);
    }
    public static void playerCount(String serverName,Consumer<Integer> consumer){
        Main plugin = Main.getPlugin();
        sendPluginMessageAny("PlayerCount "+serverName);
        new BukkitRunnable() {
            @Override
            public void run() {
                Main plugin = Main.getPlugin();
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();
                int playerCount = config.getInt("serverPlayerAmount."+serverName);
                config.set("serverPlayerAmount."+serverName,null);
                cconfig.save();
                consumer.accept(playerCount);

            }
        }.runTaskLaterAsynchronously(plugin, 10);
    }
    public static void playerList(String serverName,Consumer<String[]> consumer){
        Main plugin = Main.getPlugin();
        sendPluginMessageAny("PlayerList "+serverName);
        new BukkitRunnable() {
            @Override
            public void run() {
                Main plugin = Main.getPlugin();
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();

                String playerList = config.getString("serverPlayerList."+serverName);
                config.set("playerList"+serverName,null);
                cconfig.save();
                if(playerList!=null) consumer.accept(playerList.split(", "));
                else consumer.accept(new String[]{});
            }
        }.runTaskLaterAsynchronously(plugin, 10);
    }
    public static void serverList(Consumer<String[]> consumer){
        Main plugin = Main.getPlugin();
        sendPluginMessageAny("GetServers");
        new BukkitRunnable() {
            @Override
            public void run() {
                Main plugin = Main.getPlugin();
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();

                String serverList = config.getString("serverList");
                config.set("serverList",null);
                cconfig.save();
                if(serverList!=null) consumer.accept(serverList.split(", "));
                else consumer.accept(new String[]{});
            }
        }.runTaskLaterAsynchronously(plugin, 10);
    }
    public static void message(String playerName,String message){
        sendPluginMessageAnyRegex("Message⁻"+playerName+"⁻"+message,"⁻");
    }
    public static void messageRaw(String playerName,String message){
        sendPluginMessageAnyRegex("MessageRaw⁻"+playerName+"⁻"+message,"⁻");
    }
    public static void kick(String playerName,String reason){
        sendPluginMessageAnyRegex("KickPlayer⁻"+playerName+"⁻"+reason,"⁻");
    }
    public static void custom(String serverName, String channelName, ArrayList<Object> data){
        Plugin plugin = Main.getPlugin();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward"); // So BungeeCord knows to forward it
        out.writeUTF(serverName);
        out.writeUTF(channelName); // The channel name to check if this your data

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            for (Object o:
                 data) {
                if(o.getClass().equals(String.class)) msgout.writeUTF((String) o);
                else if(o.getClass().equals(Short.class)) msgout.writeShort((Short) o);
                else if(o.getClass().equals(Integer.class)) msgout.writeInt((Integer) o);
                else if(o.getClass().equals(Long.class)) msgout.writeLong((Long) o);
                else if(o.getClass().equals(Boolean.class)) msgout.writeBoolean((Boolean) o);
                else if(o.getClass().equals(Double.class)) msgout.writeDouble((Double) o);
                else if(o.getClass().equals(Float.class)) msgout.writeFloat((Float) o);
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if(player != null) player.sendPluginMessage(plugin,"BungeeCord",out.toByteArray());
        else Log.error("No player online!");
    }

    public static void isServerOnline(String serverName, Consumer<Boolean> consumer){
        Main plugin = Main.getPlugin();
        sendPluginMessageAny("ServerIP "+serverName);
        new BukkitRunnable() {
            @Override
            public void run() {
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();

                boolean online = checkIP(config.getString("serverIP."+serverName+".ip"), config.getInt("serverIP."+serverName+".port"));

                if (online) {
                    config.set("serveronline."+serverName,null);
                    cconfig.save();
                }
                consumer.accept(online);
            }
        }.runTaskLaterAsynchronously(plugin, 10);
    }


    public static void sendPluginMessageRegex(Player player, String args, String regex){
        Plugin plugin = Main.getPlugin();
        ByteArrayDataOutput arguments = ByteStreams.newDataOutput();

        String[] argumentList = args.split(regex);
        for(String arg : argumentList){
            arguments.writeUTF(arg);
        }
        if(player != null) player.sendPluginMessage(plugin,"BungeeCord",arguments.toByteArray());
        else Log.error("No player online!");
    }
    public static void sendPluginMessage(Player player, String args){
        sendPluginMessageRegex(player, args, " ");
    }
    public static void sendPluginMessageAnyRegex(String args, String regex){
        sendPluginMessageRegex(Iterables.getFirst(Bukkit.getOnlinePlayers(), null),args,regex);
    }
    public static void sendPluginMessageAny(String args){
        sendPluginMessage(Iterables.getFirst(Bukkit.getOnlinePlayers(), null),args);
    }

    private static boolean checkIP(String ip, int port) {
        Log.log(ChatColor.YELLOW + "Pinging " + ip + ":" + port);
        boolean reachable =false;
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 20);
            s.close();
            Log.log(ChatColor.GREEN + ip + ":" + port + " is reachable");
            reachable = true;
        } catch (IOException e) {
            Log.log(ChatColor.RED + ip + ":" + port + " is offline");
        }
        return reachable;
    }

    public static void serversInit(){
        if(!Bukkit.getOnlinePlayers().isEmpty()) {
            serverList((serverL)-> {
                Main plugin = Main.getPlugin();
                CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
                FileConfiguration config = cconfig.getCustomConfig();
                if(serverL.length != 0) config.set("servers",Arrays.asList(serverL));
                cconfig.save();
            });
        }
    }
    public static List<String> getServers(){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.BUNGEE_DATA, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        List<String> serverL = config.getStringList("servers");
        if(serverL.isEmpty()) serversInit();

        return serverL;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        new BukkitRunnable() {
            @Override
            public void run() {
                serversInit();
            }
        }.runTaskLater(Main.getPlugin(),10);
    }
}
