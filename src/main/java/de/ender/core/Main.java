package de.ender.core;

import de.ender.core.afk.AfkCMD;
import de.ender.core.afk.AfkManager;
import de.ender.core.customItems.CustomFoodItemListener;
import de.ender.core.customItems.CustomItem;
import de.ender.core.customItems.CustomUsableItemListener;
import de.ender.core.events.PlayerInventoryChangeEventListener;
import de.ender.core.floattext.CustomFloatTextManager;
import de.ender.core.floattext.FloatTextCMD;
import de.ender.core.floattext.FloatTextManager;
import de.ender.core.guiManagers.GuiListener;
import de.ender.core.modifiers.ModifierManager;
import de.ender.core.customItems.CustomItemsCMD;
import de.ender.core.weapons.WeaponListener;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static final String BUNGEE_DATA = "BungeeData";

    @Override
    public void onEnable() {
        Log.enable(this);
        Log.printLogo();
        plugin = this;
        new UpdateChecker(this,"main").check().downloadLatestMeins();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new AfkManager(), this);
        pluginManager.registerEvents(new PluginMessageManager(), this);
        pluginManager.registerEvents(new ModifierManager(), this);
        pluginManager.registerEvents(new GuiListener(), this);
        pluginManager.registerEvents(new WeaponListener(), this);
        pluginManager.registerEvents(new CustomUsableItemListener(), this);
        pluginManager.registerEvents(new CustomFoodItemListener(), this);
        pluginManager.registerEvents(new PlayerInventoryChangeEventListener(), this);

        getCommand("afk").setExecutor(new AfkCMD());
        getCommand("customitems").setExecutor(new CustomItemsCMD());
        getCommand("customitems").setTabCompleter(new TabCompleter().addCompI(0, CustomItem::getNames));
        getCommand("floattext").setExecutor(new FloatTextCMD());
        getCommand("floattext").setTabCompleter(new TabCompleter()
                .addCompI(0,"add","remove","set","put")
                .addMultiPathedComp(FloatTextManager::getIDList,"remove","set.x")
                .addPredicateComp((i)-> i>=6,()->{
                    ArrayList<String> players = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach((player)-> players.add(player.getName()));
                    return players;})
                .addMultiPathedComp(()->{
                    ArrayList<String> players = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach((player)-> players.add(player.getName()));
                    return players;},"set.text.x","set.text.x.x","set.text.x.x.x")
                .addPathedComp("set","text","shadowed","billboard","rotation","seethrough")
                .addMultiPathedComp(new String[]{"True","False"},"add.x","set.shadowed.x","set.seethrough.x","add.x.x.x.x")
                .addMultiPathedComp(()->{
                    ArrayList<String> settings = new ArrayList<>();
                    for (Display.Billboard billboard:
                        Display.Billboard.values()) {
                        settings.add(billboard.name());
                    }
                    return settings;
                },"set.billboard.x", "add.x.x")
                .addPathedComp("put", CustomFloatTextManager::getCustomFloatTexts)
                .addMultiPathedComp(new String[]{"0", "-90", "90"},"add.x.x.x","set.rotation.x.x")
                .addMultiPathedComp(new String[]{"0","-180","180"},"set.rotation.x")
        );
        //PluginMessageManager
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageManager());

        //add players to afkManager after reload
        for(Player player : Bukkit.getOnlinePlayers()){
            AfkManager.playerJoin(player);
        }

        //try to auto re-initialize serverList
        PluginMessageManager.serversInit();

        registerGlow();

        FloatTextManager.init();
    }

    @Override
    public void onDisable() {
        Log.disable(this);
        Log.printLogo();

        Bukkit.getOnlinePlayers().iterator().forEachRemaining(AfkManager::playerLeave);

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
    public static Main getPlugin() {
        return plugin;
    }
    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow();
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException ignored){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
