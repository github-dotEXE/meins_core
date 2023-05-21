# meins_core
good api/lib plugin (aswell as being needed for many of my plugins)
## Documentation

### Update Checker
<pre>
  Checks updates for plugins on github
  
  Prequisits:
    version.txt in github repo (same place as readme.md)
    version.txt should have just 1 line with version 
    format (eg.: "1.0","v1.1","v1.2-beta") doesn't matter thou has to be the same in [STRING_version_of_this_plugin]
    OR
    it uses pom.xml (if pom.xml is in same place as readme.md)
  
  How to use it:
    UpdateChecker.check("[STRING_version_of_this_plugin]", "[STRING_github_profile]", "[STRING_repo_name]", "[STRING_branch_name]");
</pre>

### Log
<pre>
  Logs a message in console
  
  How to use it:
    Log.info([STRING_message]);
    Log.warn([STRING_message]);
    Log.error([STRING_message]);
    Log.criticalError([STRING_message]);
    Log.success([STRING_message]);
    Log.log([STRING_message]);
</pre>

### Item Builder
<pre>
  Lets you build an itemstack
  
  How to use it:
    new ItemBuilder([MATERIAL_material], [INT_amount]).build() //returns a ItemStack with said properties
    (.build() has to be at end or it will just give you the ItemBuilder instance)
    
    you can also use: 
      .setName([STRING_name]) //sets name of itemstack to [STRING_name]
      .setLore([STRING_ARRAY_lore]) //changes entire lore to [STRING_ARRAY_lore]
      .setLoreAt([INT_index], [STRING_lore]) //changes lore at [INT_index] in array to [STRING_lore]
      .addLore([STRING_lore]) //adds lore [STRING_lore] at end of current lore array
      .addEnchantment([ENCHANTMENT_enchantment], [INT_level]) //enchants item with [ENCHANTMENT_enchantment] at level [INT_level]
      .addMaxEnchantment([ENCHANTMENT_enchantment]) //enchants item with [ENCHANTMENT_enchantment] at max level
</pre>

### GUI Builder
<pre>
 WIP
</pre>

### Colorer
<pre>
  makes adjustments to strings or list of strings to make them colored
  
  Colorer...:
    .string(String string): makes "&" to "§"
    .string(String string, String index): makes index to "§"
    .list(String[] lst): makes "&" to "§" and joins Strings
    .list(String[] lst,String index): makes index to "§" and joins Strings
</pre>
### CustomConfig
<pre>
  makes it easy to use custom configs
  
  how to use:
    CConfig cconfig = new CConfig([String_Name], [MainClass_instance]);
    FileConfiguration config = cconfig.getCustomConfig();
    ...
    cconfig.save()
    
    (for further documentation see FileConfiguration documentation)
</pre>
### AFK
<pre>
  for managing AFKing
  
  AFK Command:
    command: /afk
    does: toggles afk status
  AFK Events:
    Join: whenever a player joins the AFK Management
    Start: whenever a player goes AFK
    Stop: whenever a player stops being AFK
  AFK Manager:
    playerJoin: triggers Join procedure
    playerLeave: triggers Leave procedure
    playerMove: sets AFK times and sees if should trigger Event
    isAFK: gets AFK status
    getAFKTIME: returns how long player hasn't moved
    setAFK: sets AFK status
</pre>
### NickManager
<pre>
  manages Nicks
  
  changeSkin(Player player, String skinsPlayersName):
    changes skin of player to the skin of skinsPlayersName
  resetSkin(Player player):
    changes skin of player to default skin of player
  resetName(Player player):
    changes name of player to default name of player
  changeName(Player player, String nickName):
    changes Name of player to nickName
</pre>
### TabCompleteManager
<pre>
  WIP
</pre>
### PluginMessageManager
<pre>
  requires Bungeecord!
  
  description WIP
</pre>


### QUESTIONS?
<pre>
private static boolean any_questions(){
  return !questions.isEmpty();
}

if(any_questions()) {
  try {
    join_discord("https://discord.gg/vytuPSmJ4c");
  } catch (Exception e) {
    e.printStackTrace();
  }
}
</pre>
