package de.ender.core;

import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;


public class NickManager {
    public static void changeSkin(Player player, String skinsPlayersName) {
        com.destroystokyo.paper.profile.PlayerProfile profile = player.getPlayerProfile();
        try {

            UUID uuid = Bukkit.getOfflinePlayer(skinsPlayersName).getUniqueId();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");

            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());

            JsonObject properties = JsonParser.parseReader(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String value = properties.get("value").getAsString();
            String signature = properties.get("signature").getAsString();

            profile.setProperty(new ProfileProperty("textures", value, signature));
            player.setPlayerProfile(profile);

        } catch (IllegalStateException | IOException | NullPointerException ignored) {
        }
    }

    public static void resetSkin(Player player) {
        changeSkin(player,player.getName());
    }

    public static void changeName(Player player, String nickName){
        Component nickComp = Component.text(nickName);

        player.displayName(nickComp);
        player.playerListName(nickComp);
        player.customName(nickComp);

    }
    public static void resetName(Player player){
        changeName(player,player.getName());
    }
}
